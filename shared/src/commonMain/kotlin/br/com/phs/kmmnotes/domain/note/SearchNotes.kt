package br.com.phs.kmmnotes.domain.note

import br.com.phs.kmmnotes.domain.time.DateTimeUtil

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {
        return if (query.isBlank()) {
            notes
        } else {
            notes.filter {
                it.title.trim().lowercase().contains(query.lowercase()) ||
                        it.content.trim().lowercase().contains(query.lowercase())
            }.sortedBy { DateTimeUtil.toEpochMillis(it.created) }
        }
    }

}