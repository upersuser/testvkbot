package dev.upersuser.testvkbot.service

import dev.upersuser.testvkbot.bot.VkBot
import dev.upersuser.testvkbot.dto.VkConfirmationRequest
import dev.upersuser.testvkbot.dto.VkEventRequest
import dev.upersuser.testvkbot.dto.VkPrivateMessage
import dev.upersuser.testvkbot.exception.InvalidConfirmationException
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.model.ClientInfo
import dev.upersuser.testvkbot.model.EventData
import dev.upersuser.testvkbot.properties.VkProperties
import dev.upersuser.testvkbot.properties.WebClientProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.Instant

class VkMessageServiceTest {

    private lateinit var vkProperties: VkProperties
    private lateinit var webClientProperties: WebClientProperties

    private lateinit var bot: VkBot
    private lateinit var vkMessageService: VkMessageService

    private val vkPrivateMessage = VkPrivateMessage(
        date = Instant.now().toEpochMilli(),
        fromId = 1L,
        id = 1L,
        version = 1L,
        peerId = 2L,
        randomId = 0,
        text = "text"
    )
    private val vkClientInfo = ClientInfo(
        buttonActions = emptyList(),
        inlineKeyboard = false,
        langId = 0L,
        keyboard = true,
        carousel = true
    )
    private val vkEventData = EventData(vkPrivateMessage, vkClientInfo)
    private val vkRequest = VkEventRequest(
        15,
        "eventId",
        "5.199",
        vkEventData,
        "secret"
    )

    @BeforeEach
    fun setup() {
        vkProperties = VkProperties(
            groupId = 12345,
            confirmationString = "confirmation_string",
            secret = "secret",
            apiKey = "api_key",
            apiVersion = "5.199"
        )
        webClientProperties = WebClientProperties(5000, 5000)
        bot = mock(VkBot::class.java)
        vkMessageService = VkMessageService(bot, vkProperties)
    }

    @Test
    fun `processRequest should process VkConfirmationRequest`() {
        val request = VkConfirmationRequest(groupId = 12345)
        val response = vkMessageService.processRequest(request)
        assertEquals("confirmation_string", response)
    }

    @Test
    fun `processRequest should throw InvalidConfirmationException for wrong groupId`() {
        val request = VkConfirmationRequest(groupId = 54321)
        assertThrows<InvalidConfirmationException> {
            vkMessageService.processRequest(request)
        }
    }

    @Test
    fun `processRequest should throw InvalidEventSecretException for invalid secret`() {
        val request = vkRequest.copy(secret = "invalid_secret")

        assertThrows<InvalidEventSecretException> {
            vkMessageService.processRequest(request)
        }
    }
}