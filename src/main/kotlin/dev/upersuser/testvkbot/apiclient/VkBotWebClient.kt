package dev.upersuser.testvkbot.apiclient

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

    webClientBuilder: RestClient.Builder
) : WithLogger {

    private val vkWebClient = webClientBuilder.baseUrl("https://api.vk.com/method/").build()

    /**
     * Метод отправляет сообщение.
     *
     * @param userId получатель сообщения
     * @param text текст сообщения
     * @param randomId идентификатор сообщения для предотвращения повторной отправки
     */
    fun sendMessage(
        userId: Long,
        text: String,
        randomId: Int = 0
    ): String? {
        val uri = UriComponentsBuilder.fromUriString(VkEventApiType.MESSAGES_SEND.uri)
            .queryParam("user_id", userId)
            .queryParam("message", text)
            .queryParam("v", vkProperties.apiVersion)
            .queryParam("random_id", randomId)
            .build()
            .toUriString()

        return vkWebClient.get()
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
            .body(String::class.java)
    }
}