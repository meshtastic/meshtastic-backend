package org.meshtastic.backend.service

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import mu.KotlinLogging
import org.meshtastic.backend.model.ChannelDB
import org.springframework.stereotype.Component
import java.io.Closeable
import javax.annotation.PostConstruct

/**
 * Common sugar for using MQTTClient
 */
open class MQTTSubscriber(
    protected val topic: String
) : Closeable {
    protected val mqtt = MQTTClient()

    override fun close() {
        mqtt.unsubscribe(topic)
        mqtt.disconnect()
        mqtt.close(true)
    }
}