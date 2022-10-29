@echo off
set startdir=%~dp0

del /f /s /q %startdir%\build\ > nul
del /f /s /q %startdir%\laundryapp\build\ > nul
del /f /s /q %startdir%\bugtracker\build\ > nul
del /f /s /q %startdir%\app\build\ > nul
del /f /s /q %startdir%\lib_api\build\ > nul
del /f /s /q %startdir%\lib_application\build\ > nul
del /f /s /q %startdir%\lib_core\build\ > nul
del /f /s /q %startdir%\feature_home\build\ > nul

rmdir /s /q %startdir%\build\
rmdir /s /q %startdir%\app\build\
rmdir /s /q %startdir%\laundryapp\build\
rmdir /s /q %startdir%\bugtracker\build\
rmdir /s /q %startdir%\lib_api\build\
rmdir /s /q %startdir%\lib_application\build\
rmdir /s /q %startdir%\lib_core\build\
rmdir /s /q %startdir%\lib_model\build\
rmdir /s /q %startdir%\feature_home\build\
