@echo off

set dir_compilacion=%CD%\Compilacion

mkdir "%dir_compilacion%"

REM Ir a Target
cd ../target/classes/

REM Compilar cliente
REM @@@@ jar cvfm Cliente.jar manifest_cliente.txt cliente/* classes/* general/*

REM Compilar servidor
REM @@@@ jar cvfm Servidor.jar manifest_servidor.txt  servidor/* gestores/* classes/* general/*

REM Mover compilados al directorio
REM @@@@ move Cliente.jar %dir_compilacion%
REM @@@@ move Servidor.jar %dir_compilacion%

REM Mover certificado de prueba
REM @@@@ mkdir "%dir_compilacion%/certificados/"
xcopy servidor\certificados\ssl_rsa_cert.p12 "%dir_compilacion%\certificados\"

echo.
echo COMPILADO
echo.
pause
exit