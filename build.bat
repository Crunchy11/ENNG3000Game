@echo off
cd /d "%~dp0"

echo Cleaning old build...
if exist out rmdir /s /q out
if exist input rmdir /s /q input
if exist dist rmdir /s /q dist
if exist dist (
    echo Could not delete dist - close the game and try again.
    pause
    exit /b 1
)

echo Compiling...
javac -d out *.java || goto fail
xcopy images out\images /E /I /Y /Q >nul || goto fail
xcopy fonts out\fonts /E /I /Y /Q >nul || goto fail

echo Building JAR...
jar --create --file input\app.jar --main-class main -C out . || goto fail

echo Packaging exe (takes about a minute)...
jpackage --type app-image --name WhackAMole --input input --main-jar app.jar --main-class main --add-modules java.desktop --win-console --dest dist || goto fail

echo.
echo Done: dist\WhackAMole\WhackAMole.exe
pause
exit /b 0

:fail
echo.
echo Build failed - see the error above.
pause
exit /b 1