package dev.upersuser.testvkbot.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import dev.upersuser.testvkbot.dto.VkPrivateMessage

/**
 * Детали сообщения.
 */
interface VkMessage

/**
 * Детали события.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class EventData(
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes(
        JsonSubTypes.Type(value = VkPrivateMessage::class),
    )
    val message: VkMessage,
    @JsonProperty("client_info") val clientInfo: ClientInfo,
)

/**
 * Информация о пользователе.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ClientInfo(
    @JsonProperty("button_actions") val buttonActions: List<String>,
    @JsonProperty("inline_keyboard") val inlineKeyboard: Boolean,
    @JsonProperty("lang_id") val langId: Long,
    val keyboard: Boolean,
    val carousel: Boolean,
)




