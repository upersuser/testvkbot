package dev.upersuser.testvkbot.bot

import dev.upersuser.testvkbot.apiclient.VkBotWebClient
import dev.upersuser.testvkbot.dto.VkPrivateMessage
import org.springframework.stereotype.Service

@Service
class VkBot(
    private val client: VkBotWebClient
) {
    fun sendAnswer(message: VkPrivateMessage) {
        val userText = message.text

        if (userText.isBlank()) return

        val userId = message.fromId

        client.sendMessage(userId, createEchoString(userText))
    }

    private fun createEchoString(text: String, prefix: String = "Вы сказали: ") = "$prefix$text"
}