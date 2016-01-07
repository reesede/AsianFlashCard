REM @ECHO OFF
REM Execute this file to launch AFC on Windows

IF EXIST "%~dp0\AFC.jar" (
  START "AsianFlashCard" "%JAVA_HOME%\jre\bin\java" -Dfile.encoding=UTF-8 -jar "%~dp0\AFC.jar"
  )
) ELSE (
  ECHO "Could not find AFC.jar"
)
