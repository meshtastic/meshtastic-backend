package org.meshtastic.common.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.geeksville.mesh.MQTTProtos
import com.geeksville.mesh.MeshProtos
import com.geeksville.mesh.Portnums
import com.google.protobuf.GeneratedMessageV3
import com.hubspot.jackson.datatype.protobuf.ProtobufModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

/**
 * Try to convert a message to a wellknown protobuf
 */
fun decodeAsProtobuf(p: MeshProtos.MeshPacket) =
    knownProtobufPorts[Portnums.PortNum.forNumber(p.decoded.portnumValue)]?.let { protoPort ->
        val payload = p.decoded.payload.toByteArray()
        val parser = protoPort.getMethod("parseFrom", payload.javaClass)
        parser.invoke(null, payload) as GeneratedMessageV3
    }


// FIXME - use the global spring ObjectMapper instead
private val mapper = ObjectMapper().apply {
    registerModule(ProtobufModule())
}

/**
 * Try to decode a message as JSON if possible
 */
fun decodeAsJson(portNum: Int, e: MQTTProtos.ServiceEnvelope): String? {
    try {
        if (e.hasPacket() && e.packet.payloadVariantCase == MeshProtos.MeshPacket.PayloadVariantCase.DECODED) {
            // The google protobuf to json stuff doesn't seem to work reliably for binary arrays packet.decoded.payload
            //val noPayload = p.toBuilder().clearDecoded().build()
            val p = e.packet
            val packetJsonStr = mapper.writeValueAsString(p)
            val packetJSON = Json.parseToJsonElement(packetJsonStr) as JsonObject

            fun makeResult(newDecoded: JsonElement): String {
                val newJSON = buildJsonObject {
                    // move over the old values - except decoded
                    packetJSON.filterKeys { it != "decoded " }.forEach { (k, v) ->
                        put(k, v)
                    }
                    put("decoded", newDecoded)
                }

                val env = EnvelopeJSON(e.channelId, e.gatewayId, newJSON)
                return Json.encodeToString(env)
            }

            val port = Portnums.PortNum.forNumber(portNum)
            val asProtobuf = decodeAsProtobuf(e.packet)
            val payload = p.decoded.payload.toByteArray()

            when {
                knownTextPorts.contains(port) -> {
                    val asStr = payload.toString(Charsets.UTF_8)

                    return makeResult(Json.encodeToJsonElement(asStr))
                }
                asProtobuf != null -> {
                    val json = mapper.writeValueAsString(asProtobuf)
                    val decodedMap = Json.parseToJsonElement(json)

                    return makeResult(Json.encodeToJsonElement(decodedMap))
                }

                else -> {
                }
            }
        }
    } catch (ex: Exception) {
        println("I suck")
    }

    return null
}