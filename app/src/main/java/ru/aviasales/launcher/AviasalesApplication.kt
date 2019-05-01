package ru.aviasales.launcher

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.aviasales.di.modules

class AviasalesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules)
    }
}