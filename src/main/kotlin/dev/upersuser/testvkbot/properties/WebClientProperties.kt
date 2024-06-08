package dev.upersuser.testvkbot.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "webclient")
data class WebClientProperties(
    val connectTimeout: Int,
    val readTimeout: Int
)
