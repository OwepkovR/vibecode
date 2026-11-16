package com.example.notes

import android.app.Application
import com.example.notes.core.data.di.dataModule
import com.example.notes.core.database.di.databaseModule
import com.example.notes.core.domain.di.domainModule
import com.example.notes.feature.di.notesFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Composition root: только :app знает обо всех модулях и собирает граф зависимостей.
 */
class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NotesApp)
            modules(
                databaseModule,
                dataModule,
                domainModule,
                notesFeatureModule,
            )
        }
    }
}
