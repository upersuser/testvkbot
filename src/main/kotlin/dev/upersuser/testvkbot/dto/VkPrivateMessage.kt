package dev.upersuser.testvkbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import dev.upersuser.testvkbot.model.VkMessage

@JsonIgnoreProperties(ignoreUnknown = true)
data class VkPrivateMessage(
    val date: Long,
    @JsonProperty("from_id") val fromId: Long,
    val id: Long,
    val version: Long,
    @JsonProperty("peer_id") val peerId: Long,
    @JsonProperty("random_id") val randomId: Long,
    val text: String,
) : VkMessage