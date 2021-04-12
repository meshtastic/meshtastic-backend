package org.meshtastic.backend.mqtt

import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.Closeable
import javax.annotation.PostConstruct

/**
 * Common sugar for using MQTTClient
 */
abstract class MQTTSubscriber(
    protected val topic: String
) : Closeable {
    private val logger = KotlinLogging.logger {}
    protected val mqtt = MQTTClient()

    override fun close() {
        mqtt.unsubscribe(topic)
        mqtt.disconnect()
        mqtt.close(true)
    }

    abstract fun onTopicReceived(msg: MqttMessage)

    @PostConstruct
    fun initialize() {
        logger.info("Subscribing to $topic")

        mqtt.subscribe(topic) { _, msg ->
            try {
                onTopicReceived(msg)
            } catch (ex: Exception) {
                // Probably bad PSK
                logger.error("Topic $topic, ignored due to $ex")
            }
        }
    }
}