package com.example.moodlogger

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MoodDetailActivity : AppCompatActivity() {

    private lateinit var moodTextView: TextView
    private lateinit var notesTextView: TextView
    private lateinit var deleteMoodButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_detail)

        moodTextView = findViewById(R.id.moodTextView)
        notesTextView = findViewById(R.id.notesTextView)
        deleteMoodButton = findViewById(R.id.deleteButton)

        val mood = intent.getStringExtra("MOOD") ?: "No Mood"
        val notes = intent.getStringExtra("NOTES") ?: "No Notes"

        moodTextView.text = mood
        notesTextView.text = notes

        deleteMoodButton.setOnClickListener {

            deleteMoodLog(mood, notes)


            Toast.makeText(this, "Mood deleted", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

    private fun deleteMoodLog(mood: String, notes: String) {
        val dbHelper = MoodDatabaseHelper(this)
        dbHelper.deleteMood(mood, notes)
    }
}

