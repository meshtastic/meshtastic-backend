package org.meshtastic.backend

import mu.KotlinLogging
import org.meshtastic.backend.model.ChannelDB
import org.meshtastic.backend.service.DecoderDaemon

fun main(args: Array<String>) {
     val logger = KotlinLogging.logger {}

    logger.info("Meshtastic Backend starting...")
    val channels = ChannelDB()
    val decoder = DecoderDaemon(channels)

    logger.info("Waiting for exit...")
    Thread.sleep(1000L * 60 * 60 * 24 * 365) // FIXME
}
