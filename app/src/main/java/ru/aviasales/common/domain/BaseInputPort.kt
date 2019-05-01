package ru.aviasales.common.domain

interface BaseInputPort<T> {

    fun attachOutputPort(outputPort: T)

    fun detachOutputPort()
}