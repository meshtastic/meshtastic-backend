package org.meshtastic.backend.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ServiceStatus(val text: String, val numConnected: Int)

@RestController
class StatusController {
    @RequestMapping("$apiPrefix/status")
    fun message() = ServiceStatus("testing", 6)
}