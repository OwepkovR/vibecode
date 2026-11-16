package com.example.notes.core.domain.usecase

import com.example.notes.core.domain.repository.NotesRepository
import com.example.notes.core.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Каждый use case выполняет ровно одну операцию (Single Responsibility),
 * что упрощает тестирование и переиспользование бизнес-логики.
 */
class ObserveNotesUseCase(private val repository: NotesRepository) {
    operator fun invoke(): Flow<List<Note>> = repository.observeNotes()
}

class GetNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(id: Long): Note? = repository.getNote(id)
}

class AddNoteUseCase(private val repository: NotesRepository) {

    /** Валидация входных данных — часть бизнес-правил домена. */
    suspend operator fun invoke(title: String, content: String): Result<Long> {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) {
            return Result.failure(IllegalArgumentException("Заголовок не может быть пустым"))
        }
        return runCatching {
            repository.addNote(Note(title = trimmedTitle, content = content))
        }
    }
}

class UpdateNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(note: Note): Result<Unit> {
        val trimmedTitle = note.title.trim()
        if (trimmedTitle.isEmpty()) {
            return Result.failure(IllegalArgumentException("Заголовок не может быть пустым"))
        }
        return runCatching {
            repository.updateNote(note.copy(title = trimmedTitle, updatedAt = System.currentTimeMillis()))
        }
    }
}

class DeleteNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteNote(id)
}
