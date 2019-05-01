package ru.aviasales.citysearch.router

sealed class SearchStrategy {

    object From: SearchStrategy()

    object To: SearchStrategy()
}