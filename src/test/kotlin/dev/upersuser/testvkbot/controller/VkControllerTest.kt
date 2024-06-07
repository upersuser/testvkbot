package dev.upersuser.testvkbot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sun.jdi.request.EventRequest
import dev.upersuser.testvkbot.dto.VkConfirmationRequest
import dev.upersuser.testvkbot.dto.VkEventRequest
import dev.upersuser.testvkbot.dto.VkPrivateMessage
import dev.upersuser.testvkbot.dto.VkRequest
import dev.upersuser.testvkbot.model.ClientInfo
import dev.upersuser.testvkbot.model.EventData
import dev.upersuser.testvkbot.service.VkServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(VkController::class)
class VkControllerTest {

    private val groupId: Long = 0L
    private val eventId: String = "eventId"

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vkService: VkServiceImpl

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = jacksonObjectMapper()
    }

    @Test
    fun `should process VkConfirmationRequest`() {
        val vkRequest = VkConfirmationRequest(groupId)
        val requestJson = objectMapper.writeValueAsString(vkRequest)

        `when`(vkService.processRequest(vkRequest)).thenReturn("ok")

        mockMvc.perform(post("/vkbot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk)
            .andExpect(content().string("ok"))

        verify(vkService, times(1)).processRequest(vkRequest)
    }

    @Test
    fun `should process VkEventRequest`() {
        val vkPrivateMessage = VkPrivateMessage(
            date = Instant.now().toEpochMilli(),
            fromId = 1L,
            id = 1L,
            version = 1L,
            peerId = 2L,
            randomId = 0,
            text = "text"
        )
        val vkClientInfo = ClientInfo(
            buttonActions = emptyList(),
            inlineKeyboard = false,
            langId = 0L,
            keyboard = true,
            carousel = true
        )
        val vkEventData = EventData(
            vkPrivateMessage,
            vkClientInfo
        )
        val vkRequest = VkEventRequest(
            groupId,
            eventId,
            "5.199",
            vkEventData,
            "secret"
        )
        val requestJson = objectMapper.writeValueAsString(vkRequest)

        `when`(vkService.processRequest(vkRequest)).thenReturn("ok")

        mockMvc.perform(post("/vkbot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk)
            .andExpect(content().string("ok"))

        verify(vkService, times(1)).processRequest(vkRequest)
    }
}