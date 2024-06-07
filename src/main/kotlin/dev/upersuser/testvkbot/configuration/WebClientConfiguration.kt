package dev.upersuser.testvkbot.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun webClient(): RestClient.Builder = RestClient.builder()
}