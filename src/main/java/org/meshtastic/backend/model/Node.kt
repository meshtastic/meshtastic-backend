package org.meshtastic.backend.model

import com.geeksville.mesh.MeshProtos
import java.util.*

data class Node(
    val id: String,
    var user: MeshProtos.User? = null,
    var position: MeshProtos.Position? = null,

    // doesn't work?
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    var lastHeard: Date = Date()
)