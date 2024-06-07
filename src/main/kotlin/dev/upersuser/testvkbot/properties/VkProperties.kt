package dev.upersuser.testvkbot.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk")
data class VkProperties(
    val groupId: String,
    val confirmationString: String,
    val secret: String,
    val apiKey: String,
    val apiVersion: String
)
