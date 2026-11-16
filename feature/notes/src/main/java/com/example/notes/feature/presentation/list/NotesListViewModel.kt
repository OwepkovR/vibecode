package com.example.notes.feature.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.domain.usecase.DeleteNoteUseCase
import com.example.notes.core.domain.usecase.ObserveNotesUseCase
import com.example.notes.core.model.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * UI-состояние списка заметок.
 */
sealed interface NotesUiState {
    data object Loading : NotesUiState
    data class Success(val notes: List<Note>) : NotesUiState
    data class Error(val message: String) : NotesUiState
}

/**
 * ViewModel зависит только от use case'ов domain-слоя (Clean Architecture),
 * ничего не зная о Room и репозиториях.
 */
class NotesListViewModel(
    observeNotes: ObserveNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> = observeNotes()
        .map<_, NotesUiState>(NotesUiState::Success)
        .onStart { emit(NotesUiState.Loading) }
        .catch { emit(NotesUiState.Error(it.message ?: "Неизвестная ошибка")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState.Loading,
        )

    fun onDelete(id: Long) {
        viewModelScope.launch { deleteNote(id) }
    }
}
