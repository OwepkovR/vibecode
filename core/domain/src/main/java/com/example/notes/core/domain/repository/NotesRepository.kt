package com.example.notes.core.domain.repository

import com.example.notes.core.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Абстракция хранилища заметок (Dependency Inversion, Interface Segregation).
 * Реализация предоставляется слоем data — domain ничего не знает о Room.
 */
interface NotesRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun getNote(id: Long): Note?
    suspend fun addNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}
