package dev.upersuser.testvkbot.apiclient

import com.fasterxml.jackson.databind.ObjectMapper
import dev.upersuser.testvkbot.dto.VkApiCallErrorResponse
import dev.upersuser.testvkbot.exception.VkApiCallException
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.model.VkEventApiType
import dev.upersuser.testvkbot.properties.WebClientProperties
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@Service
class VkBotWebClient(
    private val vkProperties: VkProperties,
    private val webClientProperties: WebClientProperties,
    private val objectMapper: ObjectMapper,

    webClientBuilder: RestClient.Builder,
) {
    private val logger = LoggerFactory.getLogger(VkBotWebClient::class.java)
    private val vkWebClient = webClientBuilder
        .baseUrl(VK_API_URL)
        .requestFactory(
            SimpleClientHttpRequestFactory().apply {
                setConnectTimeout(webClientProperties.connectTimeout)
                setReadTimeout(webClientProperties.readTimeout)
            }
        )
        .build()

    /**
     * Метод отправляет сообщение.
     *
     * @param userId получатель сообщения
     * @param messageText текст сообщения
     * @param randomId идентификатор сообщения для предотвращения повторной отправки
     *
     * @throws VkApiCallException
     */
    fun sendMessage(
        userId: Long,
        messageText: String,
        randomId: Int = 0,
    ) {
        val uri = UriComponentsBuilder.fromUriString(VkEventApiType.MESSAGES_SEND.uri)
            .queryParam("user_id", userId)
            .queryParam("message", messageText)
            .queryParam("v", vkProperties.apiVersion)
            .queryParam("random_id", randomId)
            .build()
            .toUriString()

        val response = try {
            vkWebClient.get()
            .uri(uri)
            .header("Authorization", "Bearer ${vkProperties.apiKey}")
            .retrieve()
            .onStatus((HttpStatusCode::is4xxClientError)) { _, response ->
                logger.error("4xx Client response status code:: ${response.statusCode}")
                throw ResponseStatusException(response.statusCode)
            }
            .onStatus((HttpStatusCode::is5xxServerError)) { _, response ->
                logger.error("5xx Client response status code:: ${response.statusCode}")
                throw ResponseStatusException(response.statusCode)
            }
            .toEntity(String::class.java)
        } catch (e: Exception) {
            if (e !is ResponseStatusException) {
                logger.error("Error while calling vk message.send method:: ${e.message}")
                logger.error("Stack trace:: ${e.stackTraceToString()}")
            }

            throw VkApiCallException()
        }

        try {
            val error = objectMapper.readValue(response.body, VkApiCallErrorResponse::class.java)
            logger.error("Vk api call exception code:: ${error.error.errorCode} message:: ${error.error.errorMsg}")

            throw VkApiCallException(error.error.errorCode, error.error.errorMsg)
        } catch (_: Exception) {}

        logger.debug("Vk api call success userId:: $userId message:: $messageText")
    }

    companion object {
        const val VK_API_URL = "https://api.vk.com/method/"
    }
}