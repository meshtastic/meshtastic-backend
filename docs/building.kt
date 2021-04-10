# Building

Notes on how to build and run.

## Minimal install instructions

sudo apt-get install openjdk-11-jdk
sudo update-alternatives --config java

./gradlew bootRun

## IDE

How to configure Intellij for development:

FIXME

## Deployment

This server is built as a Kubernete image which can be deployed in many different containers.  The instructions
below assume Google Kerbernets Engine.  (I used https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine
abd https://www.baeldung.com/dockerizing-spring-boot-application to make this).

### Docker config/commands

* snap install mosquitto
* install docker https://docs.docker.com/install/linux/docker-ce/ubuntu/
* install docker-machine https://docs.docker.com/machine/install-machine/
* start docker daemon https://docs.docker.com/install/linux/linux-postinstall/
* bin/docker-build.sh to build image
* bin/docker-run.sh to run(first number is the public port number exposed on the host)
* docker ps
* docker logs <containerid>
* docker exec -it a30a7ff10036 /bin/bash
* docker stop containerid

### configure docker-machine on my public web server

* see  https://docs.docker.com/machine/examples/ocean/
* create new machine on DOcean: docker-machine create --driver digitalocean --digitalocean-access-token sekritauthkey docker-sandbox
* eval "$(docker-machine env docker-sandbox)" <- this sets env vars so docker is now talking to the remote machine
* docker-machine ssh docker-sandbox

### Run mosquitto server in a container on my webhost

* https://hub.docker.com/_/eclipse-mosquitto <- docs
* also use docker to run it locally and on the web
* see link above to customize the config

