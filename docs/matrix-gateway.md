# Early notes on how matrix gateway might work


## How to run a matrix server locally

From @Dan-v in the devgroup:

I just started playing around with a local matrix server tonight to get a better feel for things. Here are some quick notes I jotted down for getting a local server up and running using this docker image: https://hub.docker.com/r/matrixdotorg/synapse

Start running server:

docker run -it --rm \
    --mount type=volume,src=synapse-data,dst=/data \
    -e SYNAPSE_SERVER_NAME=my.matrix.host \
    -e SYNAPSE_REPORT_STATS=no \
    matrixdotorg/synapse:latest generate
docker run -d --name synapse \
    --mount type=volume,src=synapse-data,dst=/data \
    -p 8008:8008 \
    matrixdotorg/synapse:latest
    
Register a new test/test user:

docker exec -it synapse register_new_matrix_user -u test -p test -a -c /data/homeserver.yaml http://127.0.0.1:8008

Then you can load up any matrix client (e.g. https://app.element.io/) and login to local server (http://127.0.0.1:8008) with test/test (edited) 

See more updates here: https://github.com/dan-v/mqtt-matrix-bridge-testing

## other ideas

"and fundamentally (regardless of how we populate it) at the lowest level, I bet the mqtt-matrix gateway will need to have a private db of users it knows about.  For each user it will need to keep a node id and the meshtastic-channel (including PSK) used to reach that user out in meshtastic land.
This db would be used to decrypt/encrypt on that user's behalf for messages coming in/out of matrix."

what if that database gets compromized? all those psks become public?

