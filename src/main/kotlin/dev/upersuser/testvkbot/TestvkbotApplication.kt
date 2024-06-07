package dev.upersuser.testvkbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties
@ConfigurationPropertiesScan("dev.upersuser.testvkbot.properties")
@SpringBootApplication
class TestvkbotApplication

fun main(args: Array<String>) {
    runApplication<TestvkbotApplication>(*args)
}
