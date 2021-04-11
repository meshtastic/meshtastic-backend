package org.meshtastic.backend.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class GeoJSONController {
    @RequestMapping("$apiPrefix/geoJSON/nodes")
    fun message() = ServiceStatus("testing", 6)
}