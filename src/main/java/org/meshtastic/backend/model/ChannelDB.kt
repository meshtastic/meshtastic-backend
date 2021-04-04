package org.meshtastic.backend.model

import org.meshtastic.common.model.Channel

/**
 * A database of all known channels
 */
class ChannelDB {
    // FIXME hack temporary DB with the default channel
    private val db = listOf(Channel()).map { it.name to it }.toMap()

    fun getById(id: String) = db.get(id)
}