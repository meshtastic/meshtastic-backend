package org.meshtastic.backend.service

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import mu.KotlinLogging
import org.meshtastic.common.model.decodeAsJson
import org.springframework.stereotype.Component
import java.io.Closeable
import javax.annotation.PostConstruct

/**
 * Connects via MQTT to our broker and watches for encrypted messages from devices. If it has suitable channel keys it decodes them and republishes in cleartext
 */
@Component
class ToJSONDaemon(private val mqtt: MQTTClient,
                   private val configuration: Configuration) : Closeable {
    private val logger = KotlinLogging.logger {}
    private val filter = "${configuration.cleartextRoot}#"

    @PostConstruct
    fun initialize() {
        logger.info("Creating toJSON daemon")
        mqtt.subscribe(filter) { topic, msg ->
            val e = MQTTProtos.ServiceEnvelope.parseFrom(msg.payload)

            if (e.hasPacket() && e.packet.payloadVariantCase == MeshProtos.MeshPacket.PayloadVariantCase.DECODED) {
                try {
                    // Show nodenums as standard nodeid strings
                    val nodeId = "!%08x".format(e.packet.from)

                    // See if we can also decode it as JSON
                    val json = decodeAsJson(e.packet.decoded.portnumValue, e)
                    if(json != null) {
                        val jsonTopic = "${configuration.jsonRoot}${e.channelId}/${nodeId}/${e.packet.decoded.portnum}"
                        mqtt.publish(
                            jsonTopic,
                            json.toByteArray())
                        logger.debug("Republished $jsonTopic as $json")
                    }
                }
                catch(ex: Exception) {
                    // Probably bad PSK
                    logger.warn("Topic $topic, ignored due to $ex")
                }
            }
        }
    }

    override fun close() {
        mqtt.unsubscribe(filter)
    }
}