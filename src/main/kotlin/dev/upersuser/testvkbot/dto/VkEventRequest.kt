package dev.upersuser.testvkbot.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.upersuser.testvkbot.model.EventData

/**
 * Данные запроса от пользователя.
 */
data class VkEventRequest(
    @JsonProperty("group_id") val groupId: Long,
    @JsonProperty("event_id") val eventId: String,
    val v: String,
    @JsonProperty("object") val eventData: EventData,
    val secret: String
) : VkRequest
