java -jar "C:\sjc\sjc.jar" src/ -o boot
IF %ERRORLEVEL% EQU 0 qemu-system-i386 -boot a -fda BOOT_FLP.IMG -L "C:\Program Files\qemu"