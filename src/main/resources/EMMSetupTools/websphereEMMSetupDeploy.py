import java
import sys, os
import time
lineSeparator = java.lang.System.getProperty('line.separator')
#serverName = 'server_name3'
#nodeName = 'ctgNode01'
#cellName = 'ctgCell01'
#virtualHostName = 'newVirHost2'
#initialHeapSize = '2048'
#maximumHeapSize = '2048'
#EMMPathAndFile = 'C:\Users\Administrator\Desktop\ezmaxmobile.war'

#this is passed in as a parameter in java when the script is called:
currentDirectory = sys.argv[0]

def main():
	
	createServer(serverName)

	ports = listPorts()

	setUpVirtualHost(ports)

	setJVMHeapSizes(initialHeapSize,maximumHeapSize)

	if webServerName == 'none':
		installEMMNoWebServer()
	else:
		installEMM()

	if startServer == 'true':
		startEMM()

	installationComplete()

def installationComplete():
	file = open(currentDirectory + '..\installationStatus.csv', 'w')
	file.write('true')
	file.close()

def createServer(serverName):
	writeToLog( "CREATING SERVER " + serverName + "..." )
	AdminTask.createApplicationServer(nodeName, ['-name', serverName, '-genUniquePorts', 'true'])
	AdminConfig.save()
	print "SERVER " + serverName + " CREATED"

def listPorts():
	writeToLog( "GETTING PORTS FROM " + serverName + " FOR THE VIRTUAL HOST " + virtualHostName )
	portNames = ["WC_defaulthost","WC_defaulthost_secure"]
	ports = []
        servers = AdminConfig.list( 'ServerEntry' ).splitlines()
	your_multiline_string = ""
        for server in servers:
		ServerName = server.split( '(', 1 )[ 0 ]
		if(ServerName == serverName):
			your_multiline_string += "System information: Server Name : " +  ServerName + "\n"

        		NamedEndPoints = AdminConfig.list( "NamedEndPoint" , server).split(lineSeparator)
        		for namedEndPoint in NamedEndPoints:
                		endPointName = AdminConfig.showAttribute(namedEndPoint, "endPointName" )
                		endPoint = AdminConfig.showAttribute(namedEndPoint, "endPoint" )
                		host = AdminConfig.showAttribute(endPoint, "host" )
                		port = AdminConfig.showAttribute(endPoint, "port" )
				if(endPointName in portNames):
                			print endPointName + ": "+ " HOST: " + host + " PORT: " + port
					your_multiline_string += "System information: Endpoint Name  : " +  endPointName + " Host : " + host + " port : " + port + "\n"
					ports.append(port)

	file = open('fileOutPut.txt', 'w')
	file.write(your_multiline_string)
	file.close()
	return ports

def setUpVirtualHost(ports):
	ports.append('80')
	
	### message in console vvv
	creatingString = "CREATING VIRTUALHOST " + virtualHostName + " WITH PORTS"
	for port in ports:
		creatingString += " " + port
	creatingString += "..."
	writeToLog( creatingString )
	### message in console ^^^

	cell = AdminConfig.getid('/Cell:' + cellName)
	AdminConfig.create('VirtualHost', cell,[['name', virtualHostName]]) 
	for port in ports:
		AdminConfig.create('HostAlias', AdminConfig.getid('/Cell:' + cellName + '/VirtualHost:' + virtualHostName + '/'), '[[port ' + port + '] [hostname "*"]]')
	AdminConfig.save()
	writeToLog( "VIRTUALHOST " + virtualHostName + " CREATED" )

def setJVMHeapSizes(initialHeapSize,maximumHeapSize):
	AdminTask.setJVMProperties('-serverName ' + serverName + ' -nodeName ' + nodeName + ' -initialHeapSize '+ initialHeapSize)
	AdminTask.setJVMProperties('-serverName ' + serverName + ' -nodeName ' + nodeName + ' -maximumHeapSize ' + maximumHeapSize)
	AdminConfig.save()

def installEMMNoWebServer():
	writeToLog( "INSTALLING EZMAXMOBILE ON " + serverName + "..." )
	AdminApp.install(EMMPathAndFile,'[-appname ' + appName + ' -node ' + nodeName + ' -cell ' + cellName + ' -server ' + serverName + ' -MapWebModToVH [[.* .* ' + virtualHostName + ']] -CtxRootForWebMod [[.* .* ' + contextRoot + ']]]')
	AdminConfig.save()
	writeToLog( "EZMAXMOBILE INSTALLED ON " + serverName )

def installEMM():
	writeToLog( "INSTALLING EZMAXMOBILE ON " + serverName + "..." )
	#This gets the node and Cell Info of the webServer from user input vvvv
	servers = AdminConfig.list( 'ServerEntry' ).splitlines()
	fullServerInfo = ''
	for server in servers:
		if server.find(webServerName) >= 0:
			fullServerInfo = server
	webServerCellName = fullServerInfo.split('cells/')
	webServerCellName = webServerCellName[1]
	webServerCellName = webServerCellName[:webServerCellName.index('/')]

	webServerNodeName = fullServerInfo.split('nodes/')
	webServerNodeName = webServerNodeName[1]
	webServerNodeName = webServerNodeName[:webServerNodeName.index('|')]
	#This gets the node and Cell Info of the webServer from user input ^^^^

	AdminApp.install( EMMPathAndFile,'[-appname ' + appName + ' -target WebSphere:cell=' + cellName + ',node=' + nodeName + ',server=' + serverName + '+WebSphere:cell=' + webServerCellName + ',node=' + webServerNodeName + ',server=' + webServerName + ' -MapWebModToVH [[.* .* ' + virtualHostName + ']] -CtxRootForWebMod [[.* .* ' + contextRoot + ']]]')
	AdminConfig.save()
	writeToLog( "EZMAXMOBILE INSTALLED ON " + serverName )

def startEMM():
	writeToLog( 'WAITING FOR EZMAXMOBILE TO BE READY TO START...' )
	result = AdminApp.isAppReady(appName)

	while (result == "false"):
		### Wait 5 seconds before checking again
   		time.sleep(5)

		print 'WAITING FOR EZMAXMOBILE TO BE READY TO START...'
   		result = AdminApp.isAppReady(appName)

	writeToLog( "STARTING THE SERVER " + serverName + " AND EZMAXMOBILE..." )
	AdminControl.startServer(serverName, nodeName)
	print "SERVER " + serverName + " AND EZMAXMOBILE STARTED"
	time.sleep(5)

def writeToLog(logInfo):
	print logInfo
	file = open(currentDirectory + '..\log.txt', 'a')
	file.write(logInfo + '\n')
	file.close()

def dictionary_from_file(fileName):
	fileDirectoryAndFile = currentDirectory + fileName
	user_input_file = open(fileDirectoryAndFile,"r")
	input_list = user_input_file.read()
	input_list = input_list.split("\n")
	input_list = list(input_list)
	user_input_file.close()
	user_input_dictionary = {}
	for line in input_list:
		line = line.split(',',1)
		user_input_dictionary[line[0]] = line[1]
	return user_input_dictionary

dict = dictionary_from_file('..\user_input.csv')
	
virtualHostName = dict.get('virtualHostName')
EMMPathAndFile = dict.get('EMMPathAndFile')
maximumHeapSize = dict.get('maximumHeapSize')
cellName = dict.get('cellName')
initialHeapSize = dict.get('initialHeapSize')
nodeName = dict.get('nodeName')
serverName = dict.get('serverName')
webServerName = dict.get('webServerName')
contextRoot = dict.get('contextRoot')
appName = dict.get('appName')
startServer = dict.get('startServer')
LDAPConnection = dict.get('LDAPConnection')

if __name__ == '__main__':
	main()