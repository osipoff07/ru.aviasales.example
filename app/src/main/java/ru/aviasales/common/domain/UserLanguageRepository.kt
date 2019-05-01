package ru.aviasales.common.domain

interface UserLanguageRepository {

    fun getIsoLanguageCode(): String
}