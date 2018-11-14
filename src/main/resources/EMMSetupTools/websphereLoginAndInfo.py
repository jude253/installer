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

currentDirectory = sys.argv[0]
def main():
	
	ListAllInfo()
	file = open(currentDirectory + '..\wsLoginTest.csv', 'w')
	file.write("true")
	file.close()


def ListAllInfo():
	writeToLog( "TESTING WEBSPHERE LOGIN INFO AND GETTING CURRENT WEBSPHERE INFORMATION" )
	servers = AdminConfig.list( 'ServerEntry' ).splitlines()
	cells = AdminConfig.list( 'Cell' ).splitlines()
	nodes = AdminConfig.list( 'Node' ).splitlines()
	your_multiline_string = ""
	for server in servers:
		serverName = server.split( '(', 1 )[ 0 ]
		print "System information: Server Name : " +  serverName
		NamedEndPoints = AdminConfig.list( "NamedEndPoint" , server).split(lineSeparator)
		for namedEndPoint in NamedEndPoints:
			endPointName = AdminConfig.showAttribute(namedEndPoint, "endPointName" )
			endPoint = AdminConfig.showAttribute(namedEndPoint, "endPoint" )
			host = AdminConfig.showAttribute(endPoint, "host" )
			port = AdminConfig.showAttribute(endPoint, "port" )
			if endPointName == "BOOTSTRAP_ADDRESS":
				print endPointName + ": " + " HOST: " + host + " PORT: " + port
				your_multiline_string += "Server." +  serverName + "," + server + "," + host + ","+ port + "\n"
			if endPointName == "WEBSERVER_ADDRESS":
				print endPointName + ": " + " HOST: " + host + " PORT: " + port
				your_multiline_string += "Server." +  serverName + "," + server + "," + host + ","+ port + "\n"
	for cell in cells:
		cellName = cell.split( '(', 1 )[ 0 ]
        	print "System information: Cell Name : " +  cellName
		your_multiline_string += "Cell." +  cellName + "," + cell +"\n"
	for node in nodes:
		nodeName = node.split( '(', 1 )[ 0 ]
        	print "System information: Node Name : " +  nodeName
		your_multiline_string += "Node." +  nodeName + "," + cell +"\n"
	file = open(currentDirectory + '..\AllWSInfo.csv', 'w')
	file.write(your_multiline_string)
	file.close()

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