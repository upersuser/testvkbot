package dev.upersuser.testvkbot.exception

/**
 * Ошибка подтверждения запроса от vk api.
 */
class InvalidConfirmationException(override val message: String) : RuntimeException()
