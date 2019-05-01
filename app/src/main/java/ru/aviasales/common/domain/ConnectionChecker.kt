package ru.aviasales.common.domain

interface ConnectionChecker {

    fun isConnected(): Boolean
}