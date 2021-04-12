package org.meshtastic.backend.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.geeksville.mesh.MQTTProtos
import mu.KotlinLogging
import org.meshtastic.common.model.decodeAsJson
import org.springframework.stereotype.Component

/**
 * Connects via MQTT to our broker and watches for encrypted messages from devices. If it has suitable channel keys it decodes them and republishes in cleartext
 */
@Component
class ToJSONDaemon(private val configuration: Configuration, private val mapper: ObjectMapper) :
    CleartextSubscriber(configuration) {
    private val logger = KotlinLogging.logger {}

    override fun onCleartextReceived(e: MQTTProtos.ServiceEnvelope) {
        // Show nodenums as standard nodeid strings
        val nodeId = "!%08x".format(e.packet.from)

        // See if we can also decode it as JSON
        val json = decodeAsJson(mapper, e.packet.decoded.portnumValue, e)
        if (json != null) {
            val jsonTopic = "${configuration.jsonRoot}${e.channelId}/${nodeId}/${e.packet.decoded.portnum}"
            mqtt.publish(
                jsonTopic,
                json.toByteArray()
            )
            logger.debug("Republished $jsonTopic as $json")
        }
    }
}