package org.meshtastic.backend.rest

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RootController {

    /**
     * Generate a root HTML page
     */
    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Meshtastic Backend"
        return "root"
    }

}