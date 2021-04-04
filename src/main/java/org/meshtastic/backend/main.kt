package org.meshtastic.backend

import org.meshtastic.backend.model.ChannelDB
import org.meshtastic.backend.service.DecoderDaemon

fun main(args: Array<String>) {

    println("Meshtastic Backend starting...")
    val channels = ChannelDB()
    val decoder = DecoderDaemon(channels)

}
