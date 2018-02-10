#!/bin/bash

mvn package
mvn exec:java -Dexec.mainClass="com.concurrent.lab3.Main"