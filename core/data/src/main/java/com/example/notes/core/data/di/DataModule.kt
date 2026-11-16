package com.example.notes.core.data.di

import com.example.notes.core.data.repository.NotesRepositoryImpl
import com.example.notes.core.database.dao.NoteDao
import com.example.notes.core.domain.repository.NotesRepository
import org.koin.dsl.module

val dataModule = module {
    single<NotesRepository> { NotesRepositoryImpl(dao = get()) }
}
