package org.meshtastic.backend

import mu.KotlinLogging
import org.meshtastic.backend.service.Configuration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
// @ConfigurationPropertiesScan // Needed to find service.Configuration
@EnableConfigurationProperties(Configuration::class)
class BackendApplication

fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}

    logger.info("Meshtastic Backend starting...")

    runApplication<BackendApplication>(*args)
}
