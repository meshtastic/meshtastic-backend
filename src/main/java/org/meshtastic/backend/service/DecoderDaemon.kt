package org.meshtastic.backend.service

import mu.KotlinLogging
import org.meshtastic.backend.model.ChannelDB
import java.io.Closeable

/**
 * Connects via MQTT to our broker and watches for encrypted messages from devices. If it has suitable channel keys it decodes them and republishes in cleartext
 */
class DecoderDaemon(private val channels: ChannelDB): Closeable {
    private val logger = KotlinLogging.logger {}
    private val mqtt = MQTTClient()

    private val cryptFilter = "mesh/crypt/#"

    init {
        logger.debug("Creating daemon")
        mqtt.subscribe(cryptFilter) { topic, msg ->
            logger.debug("Got $topic")
        }
    }

    override fun close() {
        mqtt.unsubscribe(cryptFilter)
        mqtt.close()
    }
}