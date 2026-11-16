package com.example.notes.core.database.mapper

import com.example.notes.core.database.entity.NoteEntity
import com.example.notes.core.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    updatedAt = updatedAt,
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    updatedAt = updatedAt,
)
