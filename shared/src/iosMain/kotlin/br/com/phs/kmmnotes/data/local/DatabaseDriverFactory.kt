package br.com.phs.kmmnotes.data.local

import br.com.phs.kmmnotes.shared.database.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "note.db")
    }

}