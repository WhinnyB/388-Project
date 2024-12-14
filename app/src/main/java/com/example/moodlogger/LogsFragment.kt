package com.example.moodlogger

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodlogger.databinding.FragmentLogsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogsFragment : Fragment() {
    private lateinit var binding: FragmentLogsBinding
    private lateinit var moodLogAdapter: MoodLogAdapter
    private val moodLogs = mutableListOf<DisplayMoodLog>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogsBinding.inflate(inflater, container, false)

        moodLogAdapter = MoodLogAdapter(requireContext(), moodLogs) { log ->
            val intent = Intent(requireContext(), MoodDetailActivity::class.java).apply {
                putExtra("MOOD", log.mood)
                putExtra("NOTES", log.notes)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            adapter = moodLogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnEnterEntry.setOnClickListener {
            val intent = Intent(requireContext(), NewEntry::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadMoodLogsFromDatabase()
    }

    private fun loadMoodLogsFromDatabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val cursor = MoodDatabaseHelper(requireContext()).getAllMoods()
                cursor.use {
                    val logs = mutableListOf<DisplayMoodLog>()
                    while (it.moveToNext()) {
                        val mood =
                            it.getString(it.getColumnIndexOrThrow(MoodDatabaseHelper.COLUMN_MOOD))
                        val notes =
                            it.getString(it.getColumnIndexOrThrow(MoodDatabaseHelper.COLUMN_NOTES))
                        logs.add(DisplayMoodLog(mood, notes))
                    }
                    withContext(Dispatchers.Main) {
                        moodLogs.clear()
                        moodLogs.addAll(logs)
                        moodLogAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}