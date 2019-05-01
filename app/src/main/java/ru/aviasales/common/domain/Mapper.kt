package ru.aviasales.common.domain

interface Mapper<F, T> {

    fun map(from: F): T
}