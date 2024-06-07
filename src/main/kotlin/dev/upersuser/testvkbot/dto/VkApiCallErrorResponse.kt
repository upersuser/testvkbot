package dev.upersuser.testvkbot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class VkApiCallErrorResponse(
    val error: Error,
)

data class Error(
    @JsonProperty("error_code") val errorCode: Int,
    @JsonProperty("error_msg") val errorMsg: String,
    @JsonProperty("request_params") val requestParams: List<RequestParams>,
)

data class RequestParams(
    val key: String,
    val value: String
)