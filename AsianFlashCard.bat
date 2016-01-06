REM @ECHO OFF
REM Execute this file to launch AsianFlashCard on Windows

IF EXIST "%~dp0\AsianFlashCard.jar" (
  START java -Dfile.encoding=UTF-8 -jar AsianFlashCard.jar
  )
) ELSE (
  ECHO "Could not find AsianFlashCard.jar"
)
