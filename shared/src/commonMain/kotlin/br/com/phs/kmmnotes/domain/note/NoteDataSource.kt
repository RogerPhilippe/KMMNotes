package br.com.phs.kmmnotes.domain.note

interface NoteDataSource {

    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>

}