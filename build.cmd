docker-compose down
call gradlew.bat clean
call gradlew.bat bootJar
docker rmi stiletto_was
start docker-compose up