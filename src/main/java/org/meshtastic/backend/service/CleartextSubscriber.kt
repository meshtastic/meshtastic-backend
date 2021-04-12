package org.meshtastic.backend.service

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.meshtastic.backend.mqtt.MQTTSubscriber

/** Shared code for any instance that wants to see received cleartext
 * FIXME, only one MQTTSubscriber should watch for this and insteand we should publish on some sort of internal eventbus
 * and use a coroutine to do onCleartextReceived
 */
abstract class CleartextSubscriber(private val configuration: Configuration) :
    MQTTSubscriber("${configuration.cleartextRoot}#") {
    private val logger = KotlinLogging.logger {}

    abstract fun onCleartextReceived(e: MQTTProtos.ServiceEnvelope)

    override fun onTopicReceived(msg: MqttMessage) {
        val e = MQTTProtos.ServiceEnvelope.parseFrom(msg.payload)

        if (e.hasPacket() && e.packet.payloadVariantCase == MeshProtos.MeshPacket.PayloadVariantCase.DECODED) {
            onCleartextReceived(e)
        } else {
            logger.error("Invalid cleartext envelope")
        }
    }
}