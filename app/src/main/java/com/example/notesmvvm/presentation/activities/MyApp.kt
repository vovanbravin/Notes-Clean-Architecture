package com.example.notesmvvm.presentation.activities

import android.app.Application
import com.example.data.NoteRepositoryImpl
import com.example.data.db.MainDatabase
import com.example.notesmvvm.di.appModule
import com.example.notesmvvm.di.dataModule
import com.example.notesmvvm.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}