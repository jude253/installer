package centralInformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BatScript {
	private String s = File.separator;
	private ProcessBuilder batFile;
	private Process executeWSScript;
	public String scriptFileName;
	public String fullPathAndFileName;
	public String EMMPathAndFile;
	public String EMMSetupTools = Variables.emmInstallerFolder + s + "EMMSetupTools" + s; 
	public List<String> args;
	
	public BatScript(String scriptFileName, String EMMPathAndFile) {
		this.scriptFileName = scriptFileName;
		this.EMMPathAndFile = EMMPathAndFile;
		this.fullPathAndFileName = Variables.emmInstallerFolder + s + "EMMSetupTools" + s + scriptFileName;
		this.batFile = new ProcessBuilder("cmd.exe", "/C", fullPathAndFileName,  EMMSetupTools, EMMPathAndFile);
		this.batFile.inheritIO();
	}
	BatScript(String scriptFileName, List<String> args) {
		this.scriptFileName = scriptFileName;
		this.args = args;
		this.fullPathAndFileName = Variables.emmInstallerFolder + s + "EMMSetupTools" + s + scriptFileName;
		List<String> command = new ArrayList<String>();
		command.add("cmd.exe");
		command.add("/C");
		command.add(fullPathAndFileName);
		command.addAll(args);
		
		this.batFile = new ProcessBuilder(command);
		this.batFile.inheritIO();
	}
	
	
	public boolean run() {
		try {
		executeWSScript = batFile.start();
	    executeWSScript.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ProcessBuilder getProcessBuilder() {
		return batFile;
	}
}
