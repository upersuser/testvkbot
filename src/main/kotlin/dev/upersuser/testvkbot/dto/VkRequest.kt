package dev.upersuser.testvkbot.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = VkConfirmationRequest::class, name = VkRequest.CONFIRMATION),
    JsonSubTypes.Type(value = VkEventRequest::class, name = VkRequest.MESSAGE_NEW),
)
sealed interface VkRequest {
    companion object EventType {
        const val CONFIRMATION = "confirmation"
        const val MESSAGE_NEW = "message_new"
    }
}


