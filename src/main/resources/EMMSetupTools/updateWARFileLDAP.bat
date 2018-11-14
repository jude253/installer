cd %1 2>> .\..\log.txt
7z d %2 mobile.properties -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 license.interpro -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 spring-emm.xml -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 businessobjects.jar -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 mboejb.jar -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 mboejbclient.jar -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z d %2 mbojava.jar -r 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
7z a %2 %1\..\changeWarFile\WEB-INF 2>> .\..\log.txt
if %errorlevel% neq 0 exit /b %errorlevel%
@echo true> .\..\updateWarFileStatus.csv
exit