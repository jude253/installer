package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.io.FileUtils;

import centralInformation.BatScript;
import centralInformation.Variables;
import centralInformation.WSAdminScript;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.FileReader;

public class ExternalService {
	//this list is used in several methods throughout this file to keep it from having to be passed between methods
	static List<String> file = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		refreshEmmSetupFolder();
	}
	
	public static void refreshEmmSetupFolder() {
		//this will delete EMMSetupFolder in the user's home directory and replace it with a clean copy and data from /exampledata/user_input.csv
		try {
		URL userInput = ExternalService.class.getResource("/exampledata/user_input.csv");
		File user_input = FileUtils.toFile(userInput);
		FileUtils.deleteDirectory(Variables.emmInstallerFolder);
		ExternalService.setUpEMMinstallationFolder();
		FileUtils.copyFileToDirectory(user_input, Variables.emmInstallerFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean setwsLoginTest() {
		String statusFromFile = "false";
		 try{statusFromFile = FileUtils.readFileToString(Variables.wsLoginTest,Charset.defaultCharset());} catch (IOException e1) {}
		 if(statusFromFile.equals("true")) {
			 return true;
		 } else {
			 return false;
		 }
    }
	
	 public static Task<?> doLoginTest(){
	        return new Task<Object>() {

	                   @Override
	                   protected Object call() throws Exception {
	                       for(int i=0; i<2;i++){
	                        Thread.sleep(1000);
	                        updateProgress(i+1, 10);
	                       
	                       }
	                       try {
	                   		
	                   		
	                   		Map<String,String> userInput = ExternalService.readFileToMap("user_input.csv");
	                   		WSAdminScript script = new WSAdminScript("websphereLoginAndInfo.py", userInput.get("websphere.username"),userInput.get("webshpere.password"),userInput.get("WebSphereBinPathAndFile"));
	                   	    script.run();

	                       	} catch (Exception e ) {e.printStackTrace();}
	  
	                	   updateProgress(1,1);
	                       return true;
	                   }
	               };
	    }
	
	public static boolean fileLocationValidation(String userInputMapKey) {
		try {
		File fileLocation = new File(Variables.userInputMap.get(userInputMapKey));
		return fileLocation.exists();
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public static void writeToLog(String textToLog) {
		try {
		List<String> priorLogInfo = ExternalService.readFile("log.txt");
		priorLogInfo.add(textToLog);
		priorLogInfo.add("");
		ExternalService.writeFile(priorLogInfo, "log.txt");
		} catch (Exception e) {
			
		}
	}
	
	public static Task<?> installEMM(){
        return new Task<Object>() {
        	@Override
        	protected Object call() throws Exception {
        		updateProgress(0,100);
				Variables.userInputMap = ExternalService.readFileToMap("user_input.csv");
				
				if(fileLocationValidation("EMMPathAndFile") == false) {
					updateProgress(10,100);
					writeToLog("ezmaxmobile war file location \"" + Variables.userInputMap.get("EMMPathAndFile") + "\" cannot be found");
					return false;
					
				}
				
				if(fileLocationValidation("MaximoEarFileLocation") == false) {
					updateProgress(10,100);
					writeToLog("MAXIMO.EAR folder location \"" + Variables.userInputMap.get("MaximoEarFileLocation") + "\" cannot be found");
					return false;
					
				}
				
				if(fileLocationValidation("WebSphereBinPathAndFile") == false) {
					updateProgress(10,100);
					writeToLog("websphere bin folder location \"" + Variables.userInputMap.get("WebSphereBinPathAndFile") + "\" cannot be found");
					return false;
					
				}
				
				ExternalService.getBusinessObjects(Variables.userInputMap.get("MaximoEarFileLocation"));
				if(Variables.userInputMap.get("LDAPConnection").equals("true")) {
					ExternalService.setUpLDAPProperties(Variables.userInputMap.get("maximoServerName"));
				}
				List<String> file = new ArrayList<>();
				springEMMHandling(file);
				updateProgress(1,100);
				file.clear();
				
				file = ExternalService.readFile("EMMSetupTools/mobile.properties");
				ExternalService.editMobileProperties(Variables.userInputMap, file);
				ExternalService.writeFile(file,"changeWarFile/WEB-INF/classes/mobile.properties");
				updateProgress(2,100);
				file.clear();
				
				file = ExternalService.readFile("EMMSetupTools/license.interpro");
				ExternalService.editFile(Variables.userInputMap, file);
				ExternalService.writeFile(file,"changeWarFile/WEB-INF/classes/license.interpro");
				updateProgress(3,100);
				
				if(Variables.userInputMap.get("LDAPConnection").equals("false")) {
					BatScript updateWARFile = new BatScript("updateWARFile.bat", Variables.userInputMap.get("EMMPathAndFile"));
					updateWARFile.run();
				} else {
					//if using LDAP connection add these extra jar files to EMM with the BAT script 
					//specifically this script removes the previous versions of the jar files being obtained in FileEditor.getBusinessObjects
					BatScript updateWARFileLDAP = new BatScript("updateWARFileLDAP.bat", Variables.userInputMap.get("EMMPathAndFile"));
					updateWARFileLDAP.run();
				}
		        updateProgress(9,100);
		        @SuppressWarnings("deprecation")
				String updateWarFileStatus = FileUtils.readFileToString(Variables.updateWarFileStatus);
		        if(updateWarFileStatus.equals("false")) {
		        	updateProgress(10,100);
		        	return false;
		        }
		        WSAdminScript EMMSetupDeploy = new WSAdminScript("websphereEMMSetupDeploy.py", Variables.userInputMap.get("websphere.username"),Variables.userInputMap.get("webshpere.password"),Variables.userInputMap.get("WebSphereBinPathAndFile"));
		        EMMSetupDeploy.run();
		        
		        @SuppressWarnings("deprecation")
				String installationStatus = FileUtils.readFileToString(Variables.installationStatus);
		        if(installationStatus.equals("false")) {
		        	updateProgress(20,100);
		        	return false;
		        }
		        updateProgress(100,100);
        		return true;
        		}
        	};
        }
	
	
	public static void springEMMHandling(List<String> file) {
		try {
		if(Variables.userInputMap.get("ezmaxmobile.offline.enabled").equals("true")) {
			file = ExternalService.readFile("EMMSetupTools/spring-emm.xml");
			for(int x = 0; x < file.size(); x++) {
				if(file.get(x).equals("	   default-lazy-init=\"true\"> <!-- false == On, true == Off -->")) {
					file.set(x, file.get(x).replaceFirst("true", "false"));
				}
				ExternalService.writeFile(file, "changeWarFile/WEB-INF/classes/spring-emm.xml");
			}
		} else {
			file = ExternalService.readFile("EMMSetupTools/spring-emm.xml");
			for(int x = 0; x < file.size(); x++) {
				if(file.get(x).equals("	   default-lazy-init=\"false\"> <!-- false == On, true == Off -->")) {
					file.set(x, file.get(x).replaceFirst("false", "true"));
				}
				ExternalService.writeFile(file, "changeWarFile/WEB-INF/classes/spring-emm.xml");
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	// adds the appropriate properties to user_input.csv for the WebSphereLDAP connection type
	//the information is formated from the AllWSInfo in this computers' EMMInstaller Folder
	public static void setUpLDAPProperties(String serverName) {
		Map<String,List<List<String>>> mapOfLists = ExternalService.wsChoiceBoxInputFull("AllWSInfo.csv");
		Map<String,String> file = ExternalService.readFileToMap("user_input.csv");
		String host;
		String port;
		String rootContext;
		String providerURL;
    	for (Map.Entry<String, List<List<String>>> entry : mapOfLists.entrySet()){
			List<List<String>> value = entry.getValue();
			for(List<String> str : value) {
				if(str.get(0).equals(serverName)) {
					
					host = str.get(2);
					port = str.get(3);
					String cellNode = str.get(1);
					cellNode = cellNode.substring(cellNode.indexOf("(")+1,cellNode.indexOf("|"));
					String[] temp = cellNode.split("/");
					rootContext = "cell/nodes/" + temp[3] + "/servers/" + serverName;
					providerURL = "corbaloc:iiop:" + host + ":" + port;
					System.out.println(str.get(0) + "  " + providerURL + " " + rootContext);
					file.put("java.naming.provider.url",providerURL);
					file.put("maximo.jndi.rootContext",rootContext);
				} 
			}
		}
    	try {
			ExternalService.writeFile(file, "user_input.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setUpEMMSetupTools() {
		for(String fileName : Variables.exportFileNames)
			exportFileFromEMMSetupTools(fileName);
	}
	
	//creates a copy in EMMSetupTools on this computer of a file from the resource folder EMMSetupTools
	public static void exportFileFromEMMSetupTools(String fileName) {
		
		try {
			URL EMMSetupToolsLocation = ExternalService.class.getResource("/"+ fileName);
			InputStream fileInputStream = EMMSetupToolsLocation.openStream();
			File destFile = new  File(Variables.emmSetUpTools.getPath() + File.separator + fileName );
			FileUtils.copyInputStreamToFile(fileInputStream, destFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	// creates Map of lists to populate the choiceboxes with information from websphere
	public static Map<String,List<String>> wsChoiceBoxInput(String filename) {
		Map<String,String> fromFile = readFileToMap(filename);
		Map<String,List<String>> mapOfLists = new HashMap<String, List<String>>();
		for (Map.Entry<String, String> entry : fromFile.entrySet())
		{
			String key = entry.getKey();
			String[] typeName = key.split("\\.");
		    if(mapOfLists.containsKey(typeName[0])) {
		    	List<String> temp = mapOfLists.remove(typeName[0]);
		    	temp.add(typeName[1]);
		    	mapOfLists.put(typeName[0],temp);
		    } else {
		    	List<String> temp = new ArrayList<String>();
		    	temp.add(typeName[1]);
		    	mapOfLists.put(typeName[0], temp);
		    }
		}
		return mapOfLists;
	}
	// creates Map of lists of lists to populate the choiceboxes with information from websphere
	public static Map<String,List<List<String>>> wsChoiceBoxInputFull(String filename) {
		Map<String,String> fromFile = readFileToMap(filename);
		Map<String,List<List<String>>> mapOfLists = new HashMap<String, List<List<String>>>();
		for (Map.Entry<String, String> entry : fromFile.entrySet())
		{	
			
			List<String> arrayCreator;
			String k = entry.getKey();
			String v = entry.getValue();
			String[] typeName = k.split("\\.");
			arrayCreator = new ArrayList<String>();
	    	arrayCreator.add(typeName[1]);
	    	arrayCreator.addAll(Arrays.asList(v.split(",")));
		    if(mapOfLists.containsKey(typeName[0])) {
		    	List<List<String>> temp = mapOfLists.remove(typeName[0]);
		    	temp.add(arrayCreator);
		    	mapOfLists.put(typeName[0],temp);
		    } else {
		    	List<List<String>> temp = new ArrayList<List<String>>();
		    	temp.add(arrayCreator);
		    	mapOfLists.put(typeName[0], temp);
		    }
		}
		return mapOfLists;
	}
	
	
	public static Image getImage(String imageName) {
		return new Image(ExternalService.class.getResourceAsStream("/images/" + imageName));
	}
	
	public static void getBusinessObjects(String maximoEarFileLocation) {
		Map<String,String> fromFile = readFileToMap("user_input.csv");
		List<String> maximoJarFiles = new ArrayList<String>();
		maximoJarFiles.add("businessobjects.jar");
		
		//if using LDAP connection add these extra jar files to EMM
		if(fromFile.get("LDAPConnection").equals("true")) {
			maximoJarFiles.add("mboejb.jar");
			maximoJarFiles.add("mboejbclient.jar");
			maximoJarFiles.add("mbojava.jar");
		}
		
		try {
			for(String jarFileName : maximoJarFiles) {
				File jarLocation = new File(maximoEarFileLocation + File.separator + jarFileName);
				File emmSetupToolsJar = new File(Variables.emmInstallerFolder.getPath() + File.separator + "changeWarFile" + File.separator + "WEB-INF" + File.separator + "lib" + File.separator + jarFileName);
				FileUtils.copyFile(jarLocation, emmSetupToolsJar);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
	}
	
	
	public static void writeFile(List<String> file,String fileName) throws IOException {
			    BufferedWriter writer = new BufferedWriter(new FileWriter(Variables.emmInstallerFolder + File.separator + fileName));
			    for(int x = 0; x < file.size(); x++) {
			    	writer.write(file.get(x));
			    	if(x < file.size() - 1)
			    		writer.newLine();
				}
			    writer.close();
			}
	
	public static void writeFile(Map<String,String> m,String fileName) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(Variables.emmInstallerFolder + File.separator + fileName));
	    int x = 0;
	    for(Map.Entry<String, String> entry : m.entrySet()) {
	        writer.write(entry.getKey() + ","+ entry.getValue());
			if(x < m.size() - 1) {
	    		writer.newLine();
	    	}
			x++;
	    }
	    writer.close();
	}
	
	public static List<String> readFile(String fileName) {
		file.clear();
		try {
		BufferedReader reader = new BufferedReader(new FileReader(Variables.emmInstallerFolder + File.separator + fileName));
		String line = reader.readLine();
		while (line != null) {
				file.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e){
			
		}
			return file;
		
	}
	
	public static Map<String,String> readFileToMap(String fileName){
		Map<String,String> mapToBeReturned = new HashMap<String,String>();
		String filePathAndFile = Variables.emmInstallerFolder + File.separator + fileName;
		File f = new File(filePathAndFile);
		try {
			if(f.exists() && !f.isDirectory()) { 

				BufferedReader reader = new BufferedReader(new FileReader(filePathAndFile));
				String line = reader.readLine();
				while (line != null) {
						String[] keyValue = line.split(",", 2);
						if(keyValue.length == 2) {
							mapToBeReturned.put(keyValue[0], keyValue[1]);
						} else {
							mapToBeReturned.put(keyValue[0],"");
						}
						line = reader.readLine();
					}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapToBeReturned;
		
	}
	
	
	
	public static List<String> readResourcesFile(String fileName) throws Exception {
		file.clear();
		InputStream is = ExternalService.class.getResourceAsStream(fileName);
		URL EMMSetupToolsLocation = ExternalService.class.getResource("/EMMSetupTools.zip");
		System.out.println(EMMSetupToolsLocation);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		String line = reader.readLine();
		
		while (line != null) {
			file.add(line);
			line = reader.readLine();
		}
		return file;
	}
	
	public static Map<String,String> readResourcesFileToMap(String fileName){
		Map<String,String> mapToBeReturned = new HashMap<String,String>();
		
		InputStream is = ExternalService.class.getResourceAsStream(fileName);

		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		try {


			String line = reader.readLine();
			while (line != null) {
					String[] keyValue = line.split(",", 2);
					if(keyValue.length == 2) {
						mapToBeReturned.put(keyValue[0], keyValue[1]);
					} else {
						mapToBeReturned.put(keyValue[0],"");
					}
					line = reader.readLine();
				}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapToBeReturned;
		
	}
	
	//edit file will have each line of a file in a list and change the "value" of a line if the part to left of  
	//the "=" matches up with a key in the map that is passed to it.  It will replace value 
	//(the part of the line after the "=") with the value of the key that matches up with the line
	public static void editFile(Map<String,String> input, List<String> file) throws Exception {
		for(int x = 0; x < file.size(); x++) {
			for (Map.Entry<String, String> entry : input.entrySet()) {
				if(file.get(x).contains(entry.getKey() + "=") && !file.get(x).contains("#")) {
					file.set(x, entry.getKey() + "=" + entry.getValue());
			    }
			}
		}
	}
	
	
	public static void editMobileProperties(Map<String,String> input, List<String> file) throws Exception {
		//all commenting and uncommenting of mobileProperties lines should be done here so that the appropriate lines are edited in the file
		
		//the toggleComment function will comment or uncomment the number of lines specified in the second parameter starting on and after the line in the first parameter
		
		//commenting and uncommenting as appropriate
		if(!input.containsValue("SQLSERVER")) {
			file = toggleComment("maximo.db.type=SQLSERVER", 4);
			file = toggleComment("#" + "maximo.db.type=" + input.get("maximo.db.type"), 4);
		}
		//in case the file does not contain the key "LDAPConnection", this will prevent an error
		if(input.containsKey("LDAPConnection")) {
			//if LDAP connection is set to true, this will take the necessary steps
			if(input.get("LDAPConnection").equals("true")) {
				//if the user input contains the value WEBLOGICLDAP that means these lines need to be uncommented
				if(input.containsValue("WEBLOGICLDAP")) {
					file = toggleComment("ezmaxmobile.connectionmethod=GENERAL", 3);
					file = toggleComment("#ezmaxmobile.connectionmethod=WEBLOGICLDAP", 3);
				}
				//if the user input contains the value WEBSPHERELDAP that means these lines need to be uncommented
				if(input.containsValue("WEBSPHERELDAP")) {
					file = toggleComment("ezmaxmobile.connectionmethod=GENERAL", 3);
					file = toggleComment("#ezmaxmobile.connectionmethod=WEBSPHERELDAP", 3);
				}
			}
		}
		
		//if the user input file contains this key, value pair that means that this part of the mobile properties should be commented or uncommneted
		//I am checking to make sure that the key exists before checking the value, in case the user did not select a value
		if(input.containsKey("ezmaxmobile.push.proxyserver.enabled")) {
			if(input.get("ezmaxmobile.push.proxyserver.enabled").equals("true")) {
				file = toggleComment("#ezmaxmobile.push.proxyserver.enabled=true", 3);
			}
		}
		
		//edit file will have each line of a file in a list and change the "value" of a line if the part to left of  
		//the "=" matches up with a key in the map that is passed to it.  It will replace value 
		//(the part of the line after the "=") with the value of the key that matches up with the line
		editFile(input,file);
	}
	
	public static List<String> toggleComment(String startLine, int numberOfLines) throws Exception {
		int index = file.indexOf(startLine);
		try {
		for( int y = 0; y < numberOfLines; y++) {
			int currentLineIndex = index + y;
			String currentLine = file.get(currentLineIndex);
			//these variables are to improve readability
			
			if(currentLine.equals("")) {
				//do nothing to empty lines
			}else if(currentLine.charAt(0) == '#') {
				//checks to see if this line is currently commented 
				if(currentLine.contains("=")) {
					//this will only uncomment the line if it is a property, leaving comments commented
					file.set(currentLineIndex, currentLine.substring(1));
				}
			} else {
				//if the current line is not commented, it will comment it out
				file.set(currentLineIndex, "#" + currentLine);
			}
		} 
		} catch (IndexOutOfBoundsException e) {
			throw new Exception("Line Not Found in toggleComment(\""+startLine+"\", "+numberOfLines+")");
		}
		return file;
			
	}
	
	
	//used in centralInformation.main.java basically on start up
	public static void setUpEMMinstallationFolder() {
		File subDirectoryBuilder = new File(Variables.emmInstallerFolder.getPath());
		
		if(!Variables.emmInstallerFolder.exists()) {
			try{
				//only to be to be done if the installer has not been run before on this computer
				Variables.emmInstallerFolder.mkdir();
				subDirectoryBuilder = Variables.classesFolder;
				subDirectoryBuilder.mkdirs();
				subDirectoryBuilder = Variables.emmSetUpTools;
				subDirectoryBuilder.mkdir();
				setUpEMMSetupTools();
				
				FileUtils.moveFileToDirectory(Variables.userInput, Variables.emmInstallerFolder, false);
		    }
			
		    catch(SecurityException se){
		    	System.out.println("User does not have permissions to create EMMinstallation Folder.  Use admin account");
		    } catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		try{
			//standard set up each time installer is run
			ExternalService.writeFile(Arrays.asList("false"), "wsLoginTest.csv");
			ExternalService.writeFile(Arrays.asList("false"), "updateWarFileStatus.csv");
			ExternalService.writeFile(Arrays.asList("false"), "installationStatus.csv");
			Variables.startTime = new Date();
			ExternalService.writeToLog(Variables.startTime.toString() + " - New Installer Session Started:");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
