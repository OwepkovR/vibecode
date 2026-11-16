package com.example.notes.feature.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.domain.usecase.AddNoteUseCase
import com.example.notes.core.domain.usecase.GetNoteUseCase
import com.example.notes.core.domain.usecase.UpdateNoteUseCase
import com.example.notes.core.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditNoteUiState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,
) {
    val canSave: Boolean get() = title.isNotBlank() && !isLoading && !isSaved
}

/**
 * Единый ViewModel для создания и редактирования заметки.
 * Логика валидации вынесена в domain-слой (AddNoteUseCase / UpdateNoteUseCase).
 */
class EditNoteViewModel(
    private val getNote: GetNoteUseCase,
    private val addNote: AddNoteUseCase,
    private val updateNote: UpdateNoteUseCase,
    private val noteId: Long,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditNoteUiState())
    val uiState: StateFlow<EditNoteUiState> = _uiState.asStateFlow()

    fun load() {
        if (noteId == 0L) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val note = getNote(noteId)
            _uiState.update {
                if (note != null) {
                    it.copy(title = note.title, content = note.content, isLoading = false)
                } else {
                    it.copy(isLoading = false, error = "Заметка не найдена")
                }
            }
        }
    }

    fun onTitleChange(value: String) = _uiState.update { it.copy(title = value, error = null) }

    fun onContentChange(value: String) = _uiState.update { it.copy(content = value, error = null) }

    fun onSave() {
        val state = _uiState.value
        if (!state.canSave) return
        viewModelScope.launch {
            val result = if (noteId == 0L) {
                addNote(state.title, state.content).map { }
            } else {
                updateNote(
                    Note(
                        id = noteId,
                        title = state.title,
                        content = state.content,
                        updatedAt = state.let { System.currentTimeMillis() },
                    ),
                )
            }
            result
                .onSuccess { _uiState.update { it.copy(isSaved = true) } }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Ошибка сохранения") }
                }
        }
    }
}
