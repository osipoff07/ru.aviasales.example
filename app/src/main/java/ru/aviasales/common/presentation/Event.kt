package ru.aviasales.common.presentation

data class Event<T>(
    private val data: T,
    private val isDelivered: Boolean = false
) {

    fun getData(): T = data

    fun getDataIfNotDelivered(): T? = if (isDelivered) {
        null
    } else {
        data
    }
}