package centralInformation;

import java.io.File;
import java.util.*;

public class Variables {
	public static boolean devMode = true;
	
	static List<String> file = new ArrayList<String>();
	public static Map<String,String> userInputMap = new HashMap<String,String>();
	static Map<String,String> input2 = new HashMap<String,String>();
	static String s = File.separator;
	public static File emmInstallerFolder = new File(System.getProperty("user.home") +s+ "EMMInstaller");
	public static File emmSetUpTools = new File(System.getProperty("user.home") +s+ "EMMInstaller" +s+ "EMMSetupTools");
	public static File classesFolder = new File(emmInstallerFolder.getPath()+s+"changeWarFile"+s+"WEB-INF"+s+"classes");
	
	public static File wsLoginTest = new File(Variables.emmInstallerFolder.getPath() +s+ "wsLoginTest.csv");
	public static File updateWarFileStatus = new File(Variables.emmInstallerFolder.getPath() +s+ "updateWarFileStatus.csv");
	public static File installationStatus = new File(Variables.emmInstallerFolder.getPath() +s+ "installationStatus.csv");
	public static File log = new File(Variables.emmInstallerFolder.getPath() +s+ "log.txt");
	
	public static File sevenZip = new  File(emmSetUpTools.getPath() +s+ "7z.exe" );
	public static File licenseInterpro = new  File(emmSetUpTools.getPath() +s+ "license.interpro" );
	public static File mobileProperties = new  File(emmSetUpTools.getPath() +s+ "mobile.properties" );
	public static File springEMM = new  File(emmSetUpTools.getPath() +s+ "spring-emm.xml" );
	public static File updateWARFileBAT = new  File(emmSetUpTools.getPath() +s+ "updateWARFile.bat" );
	public static File updateWARFileLDAPBAT = new  File(emmSetUpTools.getPath() +s+ "updateWARFileLDAP.bat" );
	public static File websphereEMMSetupDeployPY = new  File(emmSetUpTools.getPath() +s+ "websphereEMMSetupDeploy.py" );
	public static File websphereLoginAndInfoPY = new  File(emmSetUpTools.getPath() +s+ "websphereLoginAndInfo.py" );
	public static File userInput = new File(emmInstallerFolder.getPath() +s+ "EMMSetupTools" +s+ "user_input.csv");
	
	public static List<String> inputCheckBoxes; // = FileEditor.readFile("AllWSInfo.csv");
	public static Date startTime;
	public static String[] exportFileNames = {"7z.exe", "license.interpro", "mobile.properties", "spring-emm.xml", "updateWARFile.bat", 
												"updateWARFileLDAP.bat", "websphereEMMSetupDeploy.py", "websphereLoginAndInfo.py","user_input.csv"};
	
	

	
	
	
	
	
	
	
	
}
