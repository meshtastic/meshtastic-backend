package org.meshtastic.backend.service

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

/**
 * Client (wraps sign-in and paho library) for talking to our MQTT broker
 *
 * test.mosquitto.org
 */
class MQTTClient(): MqttClient("tcp://mqtt.meshtastic.org:1883", "meshtasticBackend") {
    /**
     * 0 – “at most once” semantics, also known as “fire-and-forget”. Use this option when message loss is acceptable, as it does not require any kind of acknowledgment or persistence
    1 – “at least once” semantics. Use this option when message loss is not acceptable and your subscribers can handle duplicates
    2 – “exactly once” semantics. Use this option when message loss is not acceptable and your subscribers cannot handle duplicates
     */
    private val defaultQOS = 1

    init {
        val options =  MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.connectionTimeout = 10
        connect(options)
    }

    fun publish(topic: String, msg: ByteArray) = publish(topic, msg, defaultQOS, false)
}