package org.meshtastic.backend.service

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.meshtastic.backend.mqtt.MQTTSubscriber
import org.meshtastic.common.model.decodeAsJson
import org.springframework.stereotype.Component

/**
 * Connects via MQTT to our broker and watches for encrypted messages from devices. If it has suitable channel keys it decodes them and republishes in cleartext
 */
@Component
class ToJSONDaemon(private val configuration: Configuration) : MQTTSubscriber("${configuration.cleartextRoot}#") {
    private val logger = KotlinLogging.logger {}

    override fun onTopicReceived(msg: MqttMessage) {
        val e = MQTTProtos.ServiceEnvelope.parseFrom(msg.payload)

        if (e.hasPacket() && e.packet.payloadVariantCase == MeshProtos.MeshPacket.PayloadVariantCase.DECODED) {

            // Show nodenums as standard nodeid strings
            val nodeId = "!%08x".format(e.packet.from)

            // See if we can also decode it as JSON
            val json = decodeAsJson(e.packet.decoded.portnumValue, e)
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
}