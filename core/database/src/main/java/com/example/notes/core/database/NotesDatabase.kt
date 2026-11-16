package com.example.notes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.core.database.dao.NoteDao
import com.example.notes.core.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val NAME = "notes.db"
    }
}
