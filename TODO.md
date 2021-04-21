## MQTT TODO

This is poorly structured and currently mostly private to @geeksville

## Misc geeksville todo

https://github.com/protocolbuffers/protobuf/releases/download/v3.15.8/protoc-3.15.8-linux-x86_64.zip
	"initializeCommand": "sudo apt-get update; sudo apt-get install -y openjdk-11-jdk python3-distutils; wget https://github.com/protocolbuffers/protobuf/releases/download/v3.15.8/protoc-3.15.8-linux-x86_64.zip -O /tmp/protoc.zip; cd /tmp; unzip protoc.zip; chmod a+x bin/protoc; sudo cp bin/protoc /usr/local/bin",

	"postCreateCommand": "git submodule update --init"

## RAK board

* rak wireless board, add support
* release bin
* add readme link

## device TODO

* DONE: fix "No suitable channel found for decoding, hash was 0xb1!" (not a bug, was configured as ham)
* suppress sleep (so is_router can work correctly) when we have an active mqtt connection.

## Service TODO

* expose recent messages
* find internal eventbus idea (coroutine based?)
* DONE geoJSON
* don't let untrusted users see $SYS
* don't decrypt duplicate messages
* add crashlytics
* add common exception catching wrappers
* setup cloud logging https://docs.spring.io/spring-cloud-gcp/docs/1.2.7.RELEASE/reference/html/#stackdriver-logging
* Use an internal eventbus to launch coroutines anytime we see a new cleartext message (Use this to refactor
  ToJSONDaemon and DBMaintenanceDaemon)
* DONE fix kermit docker containers
* DONE use my custom mqtt server (and passwords) as mqtt.meshtastic.org
* DONE hub.meshtastic.org for the web service
* DONE stop running ezdevice process on kermit (document process first)
* DONE run meshtastic backend on kermit
* DONE test on esp32
* DONE announce ready for brave users
* figure out how to do riot bridge
* figure out how to do global channel ids
* take break for RAK board tasks
* increase DNS interval for lug.geeksville and mqtt.meshtastic.org (to decrease # of DNS queries clients need to make)
* DONE change to java 11
* DONE setup mqtt.meshtastic.org and
* DONE use https://kotlinlang.org/docs/jvm-spring-boot-restful.html#explore-the-project-build-file
* DONE make kubernetes container - https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine
* have server deduplicate redundant messages before publishing as cleartext
* REJECTED use a free GKE autopilot cluster
* make MQTT to pushover daemon - or just use https://github.com/jpmens/mqttwarn
  and https://dan.langille.org/2018/04/15/using-mtqq-to-create-a-notification-network-mosquitto-mqttwarn-hare-and-hared/
* APRS to MQTT daemon
* change meshub password and remove from build (to prevent impersonation)
* add crashlytics (or similar) with remote log viewing
* thank ttgo, discourse and intellij
* Unify model.Channel with android code!
* Split APRS and pushover MQTT daemons into their own projects
* Change to use "bootBuildImage" to create docker images
  per https://www.baeldung.com/dockerizing-spring-boot-application

## APRS TODO

* add a real ham radio flag
* http://www.aprs-is.net/javAPRSSrvr/
* http://www.aprs-is.net/Connecting.aspx

## Python TODO

* add test case of preencrypting in the python tool (and sending through a node that lacks keys)

## Android TODO

* Have android app serve as a MQTT forwarder if needed (by sending ToRadio.wants_mqtt)
* Give place in android app for users to select which channel they are sending on (and which channels they are watching)

## Device TODO

* In MQTT:onPublish use the global channel ID to find the PSK/channel - not the (much weaker) channel hash
* DONE leave encrypted messages as forwarded (need fixes on both tx and rx sides)
* DONE have sim provide a fake wifi connection status saying connected
* DONE don't start MQTT if we don't have wifi connected
* DONE have plugin send uplinks to mesh
* have plugin send downlinks from mesh
* DONE don't decrypt messages before uplinking them to MQTT (move out of plugin)
* make a named "GlobalPub" secondary channel, enable uplink (only) for that channel. Short name on purpose.
* add python option for --mqtt-enable, create publicuplink channel
* optionally send positions on the PublicUplink channel
* DONE attempt reconnect to server if internet connectivity changes
* DONE don't bother contacting server if we don't have any uplink/downlink channels
* DONE test on ESP32
* DONE no need for python gateway to web initially: because both the simulator and ESP32 can talk wifi directly
* if simmesh_name is set in preferences, create the MQTTSimInterface using that as the global channel_id
* figure out how to use MQTT for simulator mesh network, use a special simmesh_name global channel_id? (because this
  would allow all nodes in simnet_xxx to subscribe only to those packets)
* figure out legality of hosting public mqtt servers with chat msgs
* mqtt.meshtastic.org should have VERY basic auth at launch (to prevent abuse)
* DONE do initial development inside of portduino
* DONE do as much possible on the device side (so we can eventually just have ESP32 talk directly to server)
* DONE add mqtt_server to radio prefs
* eventually add a MQTTPacket on the ToRadio & FromRadio links
* LATER: an android gateway would be useful
