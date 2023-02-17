package br.com.phs.kmmnotes.domain.note

import br.com.phs.kmmnotes.presentation.*
import kotlinx.datetime.LocalDateTime

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

    }

}
