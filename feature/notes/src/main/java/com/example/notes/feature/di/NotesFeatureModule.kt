package com.example.notes.feature.di

import com.example.notes.feature.presentation.edit.EditNoteViewModel
import com.example.notes.feature.presentation.list.NotesListViewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/**
 * DI-модуль фичи «Заметки». Подключается только в :app,
 * сам feature не знает о существовании остальных модулей приложения.
 */
val notesFeatureModule = module {

    factory { NotesListViewModel(observeNotes = get(), deleteNote = get()) }

    factory { (noteId: Long) ->
        EditNoteViewModel(
            getNote = get(),
            addNote = get(),
            updateNote = get(),
            noteId = noteId,
        )
    }
}

fun notesParams(noteId: Long) = parametersOf(noteId)
