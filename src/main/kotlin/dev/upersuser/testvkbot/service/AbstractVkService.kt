package dev.upersuser.testvkbot.service

import dev.upersuser.testvkbot.dto.VkConfirmationRequest
import dev.upersuser.testvkbot.dto.VkRequest
import dev.upersuser.testvkbot.exception.InvalidConfirmationException
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.util.WithLogger

abstract class AbstractVkService(
    private val vkProperties: VkProperties
) : WithLogger {

    /**
     * Обработка запросов от vk.
     */
    abstract fun processRequest(request: VkRequest): String

    /**
     * Обработка запроса на подтверждение сервера.
     */
    protected fun processConfirmationRequest(request: VkConfirmationRequest): String {
        logger.info("Process confirmation request:: $request")

        if (request.groupId != vkProperties.groupId)
            throw InvalidConfirmationException("Invalid groupId:: ${request.groupId}")

        return vkProperties.confirmationString
    }

    protected fun validateSecret(secret: String) {
        if (secret != vkProperties.secret) throw InvalidEventSecretException()
    }
}