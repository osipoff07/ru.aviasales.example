package ru.aviasales.common

import java.lang.Exception

sealed class Response<T> {

    data class Success<T>(
        val data: T
    ): Response<T>()

    data class Failed<T>(
        val exception: Exception
    ): Response<T>()
}