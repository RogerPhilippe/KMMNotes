package br.com.phs.kmmnotes

import br.com.phs.kmmnotes.domain.time.DateTimeUtil

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! - ${DateTimeUtil.formatNoteDate(DateTimeUtil.now())}"
    }
}