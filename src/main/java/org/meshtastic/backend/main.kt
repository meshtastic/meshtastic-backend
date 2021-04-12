package org.meshtastic.backend

import com.hubspot.jackson.datatype.protobuf.ProtobufModule
import mu.KotlinLogging
import org.meshtastic.backend.service.Configuration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@SpringBootApplication
// @ConfigurationPropertiesScan // Needed to find service.Configuration
@EnableConfigurationProperties(Configuration::class)
class BackendApplication {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun customJackson() = Jackson2ObjectMapperBuilderCustomizer {
        it.modulesToInstall(ProtobufModule())
    }
}

fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}

    logger.info("Meshtastic Backend starting...")

    runApplication<BackendApplication>(*args)
}
