package br.com.phs.kmmnotes.android.note.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.phs.kmmnotes.domain.note.Note
import br.com.phs.kmmnotes.domain.note.NoteDataSource
import br.com.phs.kmmnotes.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(NOTE_TITLE, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(IS_NOTE_TITLE_FOCUSED, false)
    private val noteContent = savedStateHandle.getStateFlow(NOTE_CONTENT, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(IS_NOTE_CONTENT_FOCUSED, false)
    private val noteColor = savedStateHandle.getStateFlow(NOTE_COLOR, Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle[NOTE_TITLE] = note.title
                    savedStateHandle[NOTE_CONTENT] = note.content
                    savedStateHandle[NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[NOTE_TITLE] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[NOTE_CONTENT] = text
    }

    fun onNoteTitleFocusedChanged(status: Boolean) {
        savedStateHandle[IS_NOTE_TITLE_FOCUSED] = status
    }

    fun onNoteContentFocusedChanged(status: Boolean) {
        savedStateHandle[IS_NOTE_CONTENT_FOCUSED] = status
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    companion object {
        private const val NOTE_TITLE = "NOTE_TITLE"
        private const val NOTE_CONTENT = "NOTE_CONTENT"
        private const val IS_NOTE_TITLE_FOCUSED = "IS_NOTE_TITLE_FOCUSED"
        private const val IS_NOTE_CONTENT_FOCUSED = "IS_NOTE_CONTENT_FOCUSED"
        private const val NOTE_COLOR = "NOTE_COLOR"
    }

}