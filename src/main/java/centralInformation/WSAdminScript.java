package centralInformation;

import java.io.File;


public class WSAdminScript {
	private String s = File.separator;
	private ProcessBuilder wsAdminScripting;
	private Process executeWSScript;
	public String scriptFileName;
	public String fullPathAndFileName;
	public String WSAdminBinFolder;
	public String user;
	public String password;

	public String correctlyFormattedSetupToolsLocation = Variables.emmInstallerFolder + s + "EMMSetupTools" + s; 
	
	
	public WSAdminScript(String scriptFileName, String user, String password, String WSAdminBinFolder) {
		this.scriptFileName = scriptFileName;
		this.WSAdminBinFolder = WSAdminBinFolder;
		this.user = user;
		this.password = password;
		this.fullPathAndFileName = Variables.emmInstallerFolder + s + "EMMSetupTools" + s + scriptFileName;
		this.wsAdminScripting = new ProcessBuilder("cmd.exe", "/C", "\"" + WSAdminBinFolder + "\"" + s + "wsadmin -lang jython -user "+user+" -password "+password+" -f \"" + fullPathAndFileName  + "\" " + correctlyFormattedSetupToolsLocation);
		this.wsAdminScripting.inheritIO();
	}
	
	
//	public static void main(String[] args) throws Exception {
//		//for debugging purposes
//
//		Script script = new Script("websphereLoginAndInfo.py", "wasadmin","Captain1","C:\\Program Files (x86)\\IBM\\WebSphere\\AppServer\\profiles\\ctgAppSrv01\\bin");
//		
//	    System.out.println(script.run());
//
//	}
	
	public boolean run() {
		try {
		executeWSScript = wsAdminScripting.start();
	    executeWSScript.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ProcessBuilder getProcessBuilder() {
		return wsAdminScripting;
	}
}
