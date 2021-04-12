package org.meshtastic.backend.model

import org.meshtastic.common.model.Channel
import org.springframework.stereotype.Component

/**
 * A database of all known channels
 */
@Component
class ChannelDB {
    // FIXME hack temporary DB with the default channel
    private val db = listOf(Channel.default).associateBy { it.name }

    fun getById(id: String) = db[id]
}