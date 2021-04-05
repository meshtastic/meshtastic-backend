package org.meshtastic.common.model

import com.geeksville.mesh.AdminProtos
import com.geeksville.mesh.MeshProtos
import com.geeksville.mesh.Portnums.PortNum
import com.geeksville.mesh.RemoteHardware

/**
 * This is a table of 'well known' portnums and their associated Protocol Buffer payloads.
 * This list will be updated based on publically documented protobufs that have been checked into the master
 * https://github.com/meshtastic/Meshtastic-protobufs
 */
val knownProtobufPorts = mapOf(
    PortNum.ADMIN_APP to AdminProtos.AdminMessage::class.java,
    PortNum.ROUTING_APP to MeshProtos.Routing::class.java,
    PortNum.POSITION_APP to MeshProtos.Position::class.java,
    PortNum.NODEINFO_APP to MeshProtos.User::class.java,
    PortNum.REMOTE_HARDWARE_APP to RemoteHardware.HardwareMessage::class.java
)

/**
 * A few portnums just contain raw text, so we try to decode them as well
 */
val knownTextPorts = setOf(
    PortNum.TEXT_MESSAGE_APP
)

