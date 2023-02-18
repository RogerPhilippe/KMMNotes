package br.com.phs.kmmnotes.android.notelist

import br.com.phs.kmmnotes.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
