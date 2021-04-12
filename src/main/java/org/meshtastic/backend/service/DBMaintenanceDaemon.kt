package org.meshtastic.backend.service

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import com.geeksville.mesh.Portnums
import mu.KotlinLogging
import org.meshtastic.backend.model.NodeDB
import org.meshtastic.common.model.decodeAsProtobuf
import org.springframework.stereotype.Component

/**
 * Updates our global node db
 *
 * FIXME should also update based on !cleartext
 * FIXME should only subscribe for position and node info updates
 */
@Component
class DBMaintenanceDaemon(private val configuration: Configuration, private val nodes: NodeDB) :
    CleartextSubscriber(configuration) {
    private val logger = KotlinLogging.logger {}

    override fun onCleartextReceived(e: MQTTProtos.ServiceEnvelope) {
        // Show nodenums as standard nodeid strings
        val nodeId = "!%08x".format(e.packet.from)
        val d = e.packet.decoded
        val n = nodes.getOrCreate(nodeId)
        val asProtobuf = decodeAsProtobuf(e.packet)
        logger.debug("Updating NodeDB $nodeId withd.portNum")
        if (asProtobuf != null)
            when (d.portnum) {
                Portnums.PortNum.POSITION_APP -> n.position = asProtobuf as MeshProtos.Position?
                Portnums.PortNum.NODEINFO_APP -> n.user = asProtobuf as MeshProtos.User?
            }
    }
}