@echo off
set startdir=%~dp0

del /f/s/q %startdir%\.gradle\ > nul
del /f/s/q %startdir%\.idea\ > nul

rmdir /s/q %startdir%\.gradle\
rmdir /s/q %startdir%\.idea\

DEL /F /S /Q /A "%startdir%\*.iml"
DEL /F /S /Q /A "%startdir%\app\*.iml"


DEL /F /S /Q /A "%startdir%\feature_home\*.iml"
DEL /F /S /Q /A "%startdir%\lib_core\*.iml"