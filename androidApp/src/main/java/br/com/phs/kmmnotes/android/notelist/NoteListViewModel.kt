package br.com.phs.kmmnotes.android.notelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.phs.kmmnotes.domain.note.Note
import br.com.phs.kmmnotes.domain.note.NoteDataSource
import br.com.phs.kmmnotes.domain.note.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchNotes = SearchNotes()

    private val notes = savedStateHandle.getStateFlow(NOTES, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SEARCH_TEXT, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IS_SEARCH_ACTIVE, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTES] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle[SEARCH_TEXT] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IS_SEARCH_ACTIVE] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle[SEARCH_TEXT] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }

    companion object {

        private const val NOTES = "notes"
        private const val SEARCH_TEXT = "searchText"
        private const val IS_SEARCH_ACTIVE = "isSearchActive"

    }

}