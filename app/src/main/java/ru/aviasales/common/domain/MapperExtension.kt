package ru.aviasales.common.domain

fun <F, T> Mapper<F, T>.mapList(
    from: List<F>
): List<T> = from.map {
    this.map(it)
}