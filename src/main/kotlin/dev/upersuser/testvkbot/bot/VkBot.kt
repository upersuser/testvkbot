package dev.upersuser.testvkbot.bot

import dev.upersuser.testvkbot.apiclient.VkBotWebClient
import dev.upersuser.testvkbot.dto.VkPrivateMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VkBot(
    private val client: VkBotWebClient
) {
    private val logger = LoggerFactory.getLogger(VkBotWebClient::class.java)

    fun sendAnswer(message: VkPrivateMessage): String {
        val userText = message.text
        if (userText.isNotBlank())
            client.sendMessage(message.fromId, createEchoString(userText))
        else
            logger.warn("The text of the user's message was blank, no reply was sent")

        return VK_SUCCESSFUL_RESPONSE
    }

    private fun createEchoString(text: String, prefix: String = "Вы сказали: ") = "$prefix$text"

    companion object {
        const val VK_SUCCESSFUL_RESPONSE = "ok"
    }
}