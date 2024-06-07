package dev.upersuser.testvkbot.service

import dev.upersuser.testvkbot.dto.VkRequest

interface VkService {

    /**
     * Обработка запросов от vk
     */
    fun processRequest(request: VkRequest): String
}