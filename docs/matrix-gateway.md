# Early notes on how matrix gateway might work

From @DanV in the devgroup:

I just started playing around with a local matrix server tonight to get a better feel for things. Here are some quick notes I jotted down for getting a local server up and running using this docker image: https://hub.docker.com/r/matrixdotorg/synapse
docker run -it --rm \
    --mount type=volume,src=synapse-data,dst=/data \
    -e SYNAPSE_SERVER_NAME=my.matrix.host \
    -e SYNAPSE_REPORT_STATS=no \
    matrixdotorg/synapse:latest generate
docker run -d --name synapse \
    --mount type=volume,src=synapse-data,dst=/data \
    -p 8008:8008 \
    matrixdotorg/synapse:latest
docker exec -it synapse register_new_matrix_user -u test -p test -a -c /data/homeserver.yaml http://127.0.0.1:8008
Then you can load up any matrix client (e.g. https://app.element.io/) and login to local server (http://127.0.0.1:8008) with test/test (edited) 
