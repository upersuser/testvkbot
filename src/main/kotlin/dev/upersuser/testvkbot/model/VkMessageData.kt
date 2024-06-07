package dev.upersuser.testvkbot.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import dev.upersuser.testvkbot.dto.VkMessageNewData

/**
 * Детали сообщения.
 */
interface VkMessageData

/**
 * Детали события.
 */
data class EventData(
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes(
        JsonSubTypes.Type(value = VkMessageNewData::class),
    )
    val message: VkMessageData,
    @JsonProperty("client_info") val clientInfo: ClientInfo,
)

/**
 * Информация о пользователе.
 */
data class ClientInfo(
    @JsonProperty("button_actions") val buttonActions: List<String>,
    @JsonProperty("inline_keyboard") val inlineKeyboard: Boolean,
    @JsonProperty("lang_id") val langId: Long,
    val keyboard: Boolean,
    val carousel: Boolean,
)




