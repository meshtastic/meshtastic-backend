set -e

# Move just our JAR into a release directory (to keep the docker file list small)
./gradlew bootJar
mkdir -p release
cp ./build/libs/backend-1.0-SNAPSHOT.jar ./release

# from https://www.baeldung.com/dockerizing-spring-boot-application
docker build --tag=meshtastic-backend:latest .