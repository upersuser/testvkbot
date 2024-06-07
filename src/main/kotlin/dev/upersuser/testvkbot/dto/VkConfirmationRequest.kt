package dev.upersuser.testvkbot.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Запрос от vk на подтверждение сервера.
 */
data class VkConfirmationRequest(
    @JsonProperty("group_id") val groupId: Long,
) : VkRequest
