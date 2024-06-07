package dev.upersuser.testvkbot.bot

import dev.upersuser.testvkbot.apiclient.VkBotWebClient
import dev.upersuser.testvkbot.dto.VkMessageNewData
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class VkBot(
    private val client: VkBotWebClient
) {
    fun sendAnswer(message: VkMessageNewData) {
        val userText = message.text

        if (userText.isBlank()) return

        val userId = message.fromId

        try {
            client.sendMessage(userId, generateEchoString(userText))
        } catch (_: ResponseStatusException) {}
    }

    private fun generateEchoString(text: String) = "Вы сказали: $text"
}