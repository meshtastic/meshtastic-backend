package org.meshtastic.backend.service

import org.meshtastic.backend.model.ChannelDB

/**
 * Connects via MQTT to our broker and watches for encrypted messages from devices. If it has suitable channel keys it decodes them and republishes in cleartext
 */
class DecoderDaemon(private val channels: ChannelDB) {
}