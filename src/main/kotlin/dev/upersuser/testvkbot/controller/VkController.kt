package dev.upersuser.testvkbot.controller

import dev.upersuser.testvkbot.dto.VkRequest
import dev.upersuser.testvkbot.service.VkService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VkController(
    private val service: VkService
) {
    @PostMapping("/vkbot")
    fun receiveVkEvent(
        @RequestBody request: VkRequest
    ): String = service.processRequest(request)
}