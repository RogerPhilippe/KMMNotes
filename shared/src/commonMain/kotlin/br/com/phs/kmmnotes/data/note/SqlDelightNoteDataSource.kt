package br.com.phs.kmmnotes.data.note

import br.com.phs.kmmnotes.domain.note.Note
import br.com.phs.kmmnotes.domain.note.NoteDataSource
import br.com.phs.kmmnotes.domain.time.DateTimeUtil
import br.com.phs.kmmnotes.shared.database.AppDatabase

class SqlDelightNoteDataSource(db: AppDatabase): NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            note.id,
            note.title,
            note.content,
            note.colorHex,
            DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return Note.mapper(queries.getNoteById(id).executeAsOneOrNull())
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList().mapNotNull { Note.mapper(it) }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }

}