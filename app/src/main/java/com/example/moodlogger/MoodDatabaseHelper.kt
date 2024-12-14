package com.example.moodlogger


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MoodDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "moodLog.db"

        private const val DATABASE_VERSION = 1

        const val TABLE_MOODS = "moods"
        const val COLUMN_ID = "_id"
        const val COLUMN_MOOD = "mood"
        const val COLUMN_NOTES = "notes"

        private const val TABLE_CREATE = """
            CREATE TABLE $TABLE_MOODS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MOOD TEXT,
                $COLUMN_NOTES TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOODS")
        onCreate(db)
    }

    fun insertMood(mood: String, notes: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MOOD, mood)
            put(COLUMN_NOTES, notes)
        }
        db.insert(TABLE_MOODS, null, values)
        db.close()
    }

    fun getAllMoods(): Cursor {
        val db = readableDatabase
        return db.query(TABLE_MOODS, null, null, null, null, null, null)
    }
    fun deleteMood(mood: String, notes: String) {
        val db = writableDatabase
        db.delete(
            TABLE_MOODS,
            "$COLUMN_MOOD = ? AND $COLUMN_NOTES = ?",
            arrayOf(mood, notes)
        )
        db.close()
    }
}
