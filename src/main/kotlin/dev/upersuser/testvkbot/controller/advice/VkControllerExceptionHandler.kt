package dev.upersuser.testvkbot.controller.advice

import dev.upersuser.testvkbot.exception.InvalidConfirmationException
import dev.upersuser.testvkbot.exception.InvalidEventSecretException
import dev.upersuser.testvkbot.exception.UnsupportedMessageTypeException
import dev.upersuser.testvkbot.exception.VkApiCallException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class VkControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(VkControllerExceptionHandler::class.java)

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleReadHttpMessageException(
        e: HttpMessageNotReadableException,
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
    ): ResponseEntity<String> = ResponseEntity("Unauthorized request", HttpStatus.FORBIDDEN)


    @ExceptionHandler(value = [UnsupportedMessageTypeException::class])
    fun handleUnsupportedMessageTypeException(
        e: UnsupportedMessageTypeException,
    ): ResponseEntity<String> = ResponseEntity.badRequest().body("The message type is not supported")

    @ExceptionHandler(value = [VkApiCallException::class])
    fun handleVkApiCallException(
        e: VkApiCallException,
    ): ResponseEntity<String> {
        return ResponseEntity.internalServerError().body("An error occurred when calling the API")
    }
}