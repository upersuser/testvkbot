package dev.upersuser.testvkbot.apiclient

import com.fasterxml.jackson.databind.ObjectMapper
import dev.upersuser.testvkbot.dto.VkApiCallErrorResponse
import dev.upersuser.testvkbot.exception.VkApiCallException
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.model.VkEventApiType
import dev.upersuser.testvkbot.util.WithLogger
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@Service
class VkBotWebClient(
    private val vkProperties: VkProperties,
    private val objectMapper: ObjectMapper,

    webClientBuilder: RestClient.Builder,
) : WithLogger {

    private val vkWebClient = webClientBuilder.baseUrl(VK_API_URL).build()

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
            if (e !is ResponseStatusException)
                logger.error("Error while sending message stack trace:: ${e.stackTraceToString()}")

            throw VkApiCallException()
        }

        val error = try {
            objectMapper.readValue(response.body, VkApiCallErrorResponse::class.java)
        } catch (_: Exception) {
            null
        }

        if (error != null) {
            logger.error("Vk api call exception code:: ${error.error.errorCode} message:: ${error.error.errorMsg}")

            throw VkApiCallException(error.error.errorCode, error.error.errorMsg)
        } else {
            logger.debug("Vk api call success userId:: $userId message:: $messageText")
        }
    }

    companion object {
        const val VK_API_URL = "https://api.vk.com/method/"
    }
}