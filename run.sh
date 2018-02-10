#!/bin/bash

mvn package
java -cp target/senate-bus-problem-1.0-SNAPSHOT-jar-with-dependencies.jar com/concurrent/lab3/Main