package dev.upersuser.testvkbot.exception

/**
 * Ошибка запроса к vk api.
 */
data class VkApiCallException(
    val vkErrorCode: Int? = null,
    override val message: String? = null
) : RuntimeException()