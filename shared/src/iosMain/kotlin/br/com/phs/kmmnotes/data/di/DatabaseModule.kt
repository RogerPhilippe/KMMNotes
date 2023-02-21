package br.com.phs.kmmnotes.data.di

import br.com.phs.kmmnotes.data.local.DatabaseDriverFactory
import br.com.phs.kmmnotes.data.note.SqlDelightNoteDataSource
import br.com.phs.kmmnotes.shared.database.AppDatabase

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource by lazy {
        SqlDelightNoteDataSource(AppDatabase(factory.createDriver()))
    }

}