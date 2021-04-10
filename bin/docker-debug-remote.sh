set -e

echo "updating container with latest code"
bin/docker-build.sh

# use our remote server
MACHINE=lug
eval "$(docker-machine env $MACHINE)"

IMAGE=meshtastic-backend
echo stopping old copies
docker rm -f $(docker ps -q --filter ancestor=$IMAGE )

echo "running container REMOTELY"
# --restart=always FIXME turn this back on

PUBLIC_PORT=80
# -p 443:8443 we are now using nginix to do SSL
docker run --interactive --rm --name meshtastic-backend -h lug.geeksville.com -p $PUBLIC_PORT:8080  -d $IMAGE
docker ps
# (first number is the public port number exposed on the host)

