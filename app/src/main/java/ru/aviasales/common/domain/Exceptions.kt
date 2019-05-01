package ru.aviasales.common.domain

import java.lang.Exception

class NotFoundException: Exception()

class NoConnectionException(): Exception()

class ServerResponseException(
    val code: Int,
    message: String,
    throwable: Throwable? = null
): Exception(message, throwable)