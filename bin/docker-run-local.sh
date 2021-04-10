echo "updating container with latest code"
bin/docker-build.sh

echo "running in local docker"
docker run -p8080:8080 meshtastic-backend:latest
