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
below assume Google Kerbernets Engine.  (I used https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine to make this).

1. Install https://cloud.google.com/sdk/docs/install
2. "gcloud components install kubectl"