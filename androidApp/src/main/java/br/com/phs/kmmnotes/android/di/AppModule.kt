package br.com.phs.kmmnotes.android.di

import android.app.Application
import br.com.phs.kmmnotes.data.local.DatabaseDriverFactory
import br.com.phs.kmmnotes.data.note.SqlDelightNoteDataSource
import br.com.phs.kmmnotes.domain.note.NoteDataSource
import br.com.phs.kmmnotes.shared.database.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(AppDatabase(driver))
    }

}