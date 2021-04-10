
# use our remote server
MACHINE=lug
eval "$(docker-machine env $MACHINE)"

IMAGE=meshtastic-backend
echo stopping old copies
docker rm -f $(docker ps -q --filter ancestor=$IMAGE )
