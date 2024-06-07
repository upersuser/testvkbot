package dev.upersuser.testvkbot.service

import dev.upersuser.testvkbot.bot.VkBot
import dev.upersuser.testvkbot.dto.VkConfirmationRequest
import dev.upersuser.testvkbot.dto.VkEventRequest
import dev.upersuser.testvkbot.dto.VkMessageNewData
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.dto.VkRequest
import dev.upersuser.testvkbot.exception.InvalidConfirmationException
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.util.WithLogger
import org.springframework.stereotype.Service

@Service
class VkServiceImpl(
    private val vkProperties: VkProperties,
    private val bot: VkBot
): VkService, WithLogger {

    override fun processRequest(request: VkRequest): String {
        return when(request) {
            is VkConfirmationRequest -> processConfirmationRequest(request)
            is VkEventRequest -> {
                if (request.secret != vkProperties.secret) throw InvalidEventSecretException()

                bot.sendAnswer(request.eventData.message as VkMessageNewData).let { "ok" }
            }
        }
    }

    /**
     * Обработка запроса на подтверждение сервера от vk
     */
    private fun processConfirmationRequest(request: VkConfirmationRequest): String {
        logger.info("Process confirmation request:: $request")

        if (request.groupId != vkProperties.groupId)
            throw InvalidConfirmationException("Invalid groupId:: ${request.groupId}")

        return vkProperties.confirmationString
    }
}