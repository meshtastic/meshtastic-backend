package org.meshtastic.backend.model

import org.springframework.stereotype.Component

/**
 * A database of all known channels
 */
@Component
class NodeDB {
    private val seedNodes = listOf<Node>()

    // FIXME temp hack DB impl
    val db = seedNodes.associateBy { it.id }.toMutableMap()

    fun getById(id: String) = db[id]
    fun getOrCreate(id: String) = db.getOrPut(id) { Node(id) }
}