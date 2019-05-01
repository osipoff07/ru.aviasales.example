package ru.aviasales.common.data

import ru.aviasales.common.domain.UserLanguageRepository
import java.util.Locale

class SystemUserLanguageRepository: UserLanguageRepository {

    override fun getIsoLanguageCode(): String = Locale.getDefault().language
}