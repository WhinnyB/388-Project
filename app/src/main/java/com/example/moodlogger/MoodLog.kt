package com.example.moodlogger

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_logs")
data class MoodLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: String,
    val notes: String
)
