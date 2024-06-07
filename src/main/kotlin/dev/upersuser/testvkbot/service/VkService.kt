package dev.upersuser.testvkbot.service

import dev.upersuser.testvkbot.bot.VkBot
import dev.upersuser.testvkbot.dto.VkConfirmationRequest
import dev.upersuser.testvkbot.dto.VkEventRequest
import dev.upersuser.testvkbot.dto.VkPrivateMessage
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.dto.VkRequest
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.exception.UnsupportedMessageTypeException
import org.springframework.stereotype.Service

@Service
class VkService(
    private val bot: VkBot,

    vkProperties: VkProperties
): AbstractVkService(vkProperties) {

    override fun processRequest(request: VkRequest): String {
        logger.debug("Receive vk request:: {}", request)

        return when(request) {
            is VkConfirmationRequest -> processConfirmationRequest(request)
            is VkEventRequest -> {
                validateSecret(request.secret)

                when (val message = request.eventData.message) {
                    is VkPrivateMessage -> bot.sendAnswer(message).let { "ok" }
                    else -> throw UnsupportedMessageTypeException()
                }
            }
        }
    }
}