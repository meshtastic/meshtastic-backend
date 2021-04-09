package org.meshtastic.backend.rest

import org.springframework.web.bind.annotation.*

data class ServiceStatus(val text: String, val numConnected: Int)

@RestController
class StatusController {
    @RequestMapping("/status")
    fun message() = ServiceStatus("testing", 6)
}