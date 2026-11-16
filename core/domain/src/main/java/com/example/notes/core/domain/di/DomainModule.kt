package com.example.notes.core.domain.di

import com.example.notes.core.domain.usecase.AddNoteUseCase
import com.example.notes.core.domain.usecase.DeleteNoteUseCase
import com.example.notes.core.domain.usecase.GetNoteUseCase
import com.example.notes.core.domain.usecase.ObserveNotesUseCase
import com.example.notes.core.domain.usecase.UpdateNoteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveNotesUseCase(repository = get()) }
    factory { GetNoteUseCase(repository = get()) }
    factory { AddNoteUseCase(repository = get()) }
    factory { UpdateNoteUseCase(repository = get()) }
    factory { DeleteNoteUseCase(repository = get()) }
}
