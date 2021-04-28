# Betriebssystem im Eigenbau

This repository contains the code I am writing for the lecture "Betriebssystem im Eigenbau"
(*self-made operating system*) by Prof. Dr. Frenz from Kempten University of Applied Sciences.

## What you need

For compiling the code you need a special compiler. The **Small Java Compiler** (SJC), written
by Prof. Dr. Frenz, has the ability to compile Java code directly into bootable images. The
compiler can be downloaded from [here](https://fam-frenz.de/stefan/compsnpe.zip).

## How you compile it

The repository can be built by using the ``sjc.jar`` within the SJC executables. For compilation
simply run ``java -jar "{PATH_TO_SJC}/sjc.jar" src/ -o boot``

## How you run it

The compiled image can be run with any emulator (or PC) which support the i386 architecture.
For example you can the start the image using QEMU: ``qemu-system-i386 -boot a -fda BOOT_FLP.IMG``