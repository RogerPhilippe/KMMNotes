package br.com.phs.kmmnotes.domain.note

import br.com.phs.database.tables.NoteEntiry
import br.com.phs.kmmnotes.presentation.*
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {

    companion object {

        private val colors = listOf(
            RED_ORANGE_HEX,
            RED_PINK_HEX,
            LIGHT_GREEN_HEX,
            BABY_BLUE_HEX,
            VIOLET_HEX
        )

        fun generateRandomColor() = colors.random()

        fun mapper(source: NoteEntiry?): Note? {

            source?: return null

            return Note(
                source.id,
                source.title,
                source.content,
                source.colorHex,
                Instant.fromEpochMilliseconds(source.created).toLocalDateTime(TimeZone.currentSystemDefault())
            )

        }

    }

}
