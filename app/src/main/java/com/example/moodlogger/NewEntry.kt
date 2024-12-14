package com.example.moodlogger

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodlogger.databinding.EnterEntryBinding

class NewEntry : AppCompatActivity() {
    private lateinit var binding: EnterEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EnterEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moods = resources.getStringArray(R.array.moods_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, moods)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.moodSpinner.adapter = spinnerAdapter

        binding.submitButton.setOnClickListener {
            val selectedMood = binding.moodSpinner.selectedItem.toString()
            val notes = binding.notesEditText.text.toString()

            if (selectedMood.isNotEmpty() && notes.isNotEmpty()) {

                val dbHelper = MoodDatabaseHelper(this)
                dbHelper.insertMood(selectedMood, notes)

                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
