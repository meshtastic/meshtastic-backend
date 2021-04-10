package org.meshtastic.common.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
class EnvelopeJSON(val channelId: String, val gatewayId: String, val packet: JsonObject)