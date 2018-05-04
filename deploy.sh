#!/usr/bin/env bash

mvn clean compile
mvn package
cd target
java -jar maven-todo-list-1.0-SNAPSHOT-jar-with-dependencies.jar
