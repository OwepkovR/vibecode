package com.example.notes.core.data.repository

import com.example.notes.core.database.dao.NoteDao
import com.example.notes.core.database.mapper.toDomain
import com.example.notes.core.database.mapper.toEntity
import com.example.notes.core.domain.repository.NotesRepository
import com.example.notes.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Реализация абстракции домена поверх Room (Dependency Inversion).
 * Модуль :core:database — единственное место, где известны Entity;
 * наружу отдаются только доменные модели.
 */
class NotesRepositoryImpl(
    private val dao: NoteDao,
) : NotesRepository {

    override fun observeNotes(): Flow<List<Note>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getNote(id: Long): Note? =
        dao.getById(id)?.toDomain()

    override suspend fun addNote(note: Note): Long =
        dao.insert(note.toEntity())

    override suspend fun updateNote(note: Note) {
        dao.update(note.toEntity())
    }

    override suspend fun deleteNote(id: Long) {
        dao.deleteById(id)
    }
}
