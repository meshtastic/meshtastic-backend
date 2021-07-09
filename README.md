# Meshtastic-backend
[![Open in Visual Studio Code](https://open.vscode.dev/badges/open-in-vscode.svg)](https://open.vscode.dev/meshtastic/meshtastic-backend)

This is the backend server code for talking to devices via our (optional) MQTT internet gateway.  It runs on hub.meshtastic.org.  

Features provided:
* Talks to our MQTT broker and decodes channels where users have registered their keys (republishing those messages as cleartext on MQTT)
* Converts cleartext messages to JSON mqtt messages.
* Provide REST endpoints of information various frontend apps might want.

## Building/running locally

We actively support and encourage new developers.  If you would like to run this software on your own machine the following instructions should get you started.  If you have other questions please post in our [forum](https://meshtastic.discourse.group/c/development) or our developer slack channel.

The following commands (if you have Java (version 8 or later installed) should 'just work' to fetch needed dependencies and run the application.

```
apt install protobuf-compiler # Or for other operating systems download here https://github.com/protocolbuffers/protobuf/releases
git clone --recurse-submodules https://github.com/meshtastic/meshtastic-backend.git
cd meshtastic-backend
./gradlew bootRun
```

It will then be serving on port 8080 (and talking to the MQTT broker)

If you prefer IDEs (I do), it is setup to work with the Intellij IDE community edition.

If you have problems getting this build to work for you, it is probably best by [watching](https://github.com/meshtastic/meshtastic-backend/actions) how github uses very similar commands to build and run each checkin.  But if that doesn't help, please post in our forum.

## License

GNU GPL V3 licensed.
Copyright 2021 Geeksville Industries, LLC.

