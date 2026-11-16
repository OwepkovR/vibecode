package com.example.notes.core.database.di

import androidx.room.Room
import com.example.notes.core.database.NotesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            NotesDatabase.NAME,
        ).build()
    }

    single { get<NotesDatabase>().noteDao() }
}
