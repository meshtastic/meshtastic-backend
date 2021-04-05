package org.meshtastic.common.model

import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import com.geeksville.mesh.Portnums
import com.google.protobuf.GeneratedMessageV3
import com.googlecode.protobuf.format.JsonFormat
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
private class EnvelopeJSON(val channelId: String, val gatewayId: String, val packet: JsonObject)

/**
 * Try to decode a message as JSON if possible
 */
fun decodeAsJson(portNum: Int, e: MQTTProtos.ServiceEnvelope): String? {
    if(e.hasPacket() && e.packet.payloadVariantCase == MeshProtos.MeshPacket.PayloadVariantCase.DECODED) {
        val p = e.packet
        val formatter = JsonFormat()
        val packetJsonStr = formatter.printToString(p)
        val packetJSON = Json.parseToJsonElement(packetJsonStr) as JsonObject

        fun makeResult(newDecoded: JsonElement): String {
            val newJSON = buildJsonObject {
                // move over the old values - except decoded
                packetJSON.filterKeys { it != "decoded "}.forEach { (k, v) ->
                    put(k, v)
                }
                put("decoded", newDecoded)
            }

            val env = EnvelopeJSON(e.channelId, e.gatewayId, newJSON)
            return Json.encodeToString(env)
        }

        val port = Portnums.PortNum.forNumber(portNum)
        val protoPort = knownProtobufPorts.get(port)
        val payload = p.decoded.payload.toByteArray()

        when {
            knownTextPorts.contains(port) -> {
                val asStr = payload.toString(Charsets.UTF_8)

                return makeResult(Json.encodeToJsonElement(asStr))
            }
            protoPort != null -> {
                val parser = protoPort.getMethod("parseFrom", payload.javaClass)
                val obj = parser.invoke(null, payload) as GeneratedMessageV3
                val json = formatter.printToString(obj)
                val decodedMap = Json.parseToJsonElement(json)

                return makeResult(Json.encodeToJsonElement(decodedMap))
            }

            else -> {}
        }
    }

    return null
}