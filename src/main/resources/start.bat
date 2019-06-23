@echo off
title hello
@echo on
java   -jar -Xms512m -Xmx512m -Xss256k -XX:MaxDirectMemorySize=128m  mkwebservice-0.0.1-SNAPSHOT.jar
@pause