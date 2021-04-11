package org.meshtastic.backend.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("org.meshtastic")
class Configuration {

    /**
     * During development we use special version names for the dev servers.  Normally this is "1"
     */
    var protoVersion: String = "unset"

    /**
     * The root for all our topics
     */
    private val topicRoot = "msh/"

    /**
     * Device versions are set by the device firmware
     */
    private val deviceVersion = "1"

    /**
     * The root for all encrypted messages
     */
    val cryptRoot get() = "$topicRoot$deviceVersion/c/"

    /**
     * The topic root for our JSON republishes
     */
    val cleartextRoot get() = "$topicRoot$protoVersion/clear/"

    /**
     * The topic root for our JSON republishes
     */
    val jsonRoot get() = "$topicRoot$protoVersion/json/"

}