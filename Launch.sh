#!/bin/bash
mvn clean
mvn package -Dmaven.test.skip=true
docker run --name mysql8-db -e MYSQL_ROOT_PASSWORD=my-secret -e MYSQL_DATABASE=drone_db -p 3300:3306 -d mysql:8.0.32