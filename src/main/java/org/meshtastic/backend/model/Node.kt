package org.meshtastic.backend.model

import com.geeksville.mesh.MeshProtos

data class Node(
    val id: String,
    var user: MeshProtos.User? = null,
    var position: MeshProtos.Position? = null
)