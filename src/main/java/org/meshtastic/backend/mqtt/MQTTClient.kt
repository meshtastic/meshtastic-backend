package org.meshtastic.backend.mqtt

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

/**
 * Client (wraps sign-in and paho library) for talking to our MQTT broker
 *
 * test.mosquitto.org
 *
 * Note: Most subscribers of MQTTClient must have their own instance, because the paho subscription code only allows one handler per unique topic string
 */
class MQTTClient : MqttClient("tcp://mqtt.meshtastic.org:1883", generateClientId(), null) {
    /**
     * 0 – “at most once” semantics, also known as “fire-and-forget”. Use this option when message loss is acceptable, as it does not require any kind of acknowledgment or persistence
    1 – “at least once” semantics. Use this option when message loss is not acceptable and your subscribers can handle duplicates
    2 – “exactly once” semantics. Use this option when message loss is not acceptable and your subscribers cannot handle duplicates
     */
    private val defaultQOS = 1

    init {
        val options = MqttConnectOptions()
        options.userName = "meshhub"
        options.password = "ugly67turtle".toCharArray()
        options.isAutomaticReconnect = true
        options.isCleanSession = false // Must be false to autoresubscribe on reconnect
        options.connectionTimeout = 10
        connect(options)
    }

    fun publish(topic: String, msg: ByteArray) = publish(topic, msg, defaultQOS, false)
}