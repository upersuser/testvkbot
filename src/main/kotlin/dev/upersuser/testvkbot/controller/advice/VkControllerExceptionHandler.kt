package dev.upersuser.testvkbot.controller.advice

import dev.upersuser.testvkbot.exception.InvalidConfirmationException
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.util.WithLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class VkControllerExceptionHandler : WithLogger {
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleReadHttpMessageException(
        e: RuntimeException,
        request: WebRequest
    ): ResponseEntity<String> {
        logger.warn("Http message was not recognized:: ${e.stackTraceToString()}")

        return ResponseEntity.ok("ok")
    }

    @ExceptionHandler(value = [InvalidConfirmationException::class])
    fun handleInvalidConfirmationRequestException(
        e: InvalidConfirmationException,
    ): ResponseEntity<String> {
        logger.info("Exception by confirmation request:: ${e.message}")

        return ResponseEntity.badRequest().body("Invalid confirmation request data")
    }

    @ExceptionHandler(value = [InvalidEventSecretException::class])
    fun handleInvalidEventSecretException(
        e: InvalidEventSecretException,
    ): ResponseEntity<String> {
        return ResponseEntity("Unauthorized request", HttpStatus.FORBIDDEN)
    }
}