package com.example.moodlogger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoodLogAdapter(
    private val context: Context,
    private val logs: MutableList<DisplayMoodLog>,
    private val onClick: (DisplayMoodLog) -> Unit
) : RecyclerView.Adapter<MoodLogAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moodTextView: TextView = itemView.findViewById(R.id.moodTextView)
        val notesTextView: TextView = itemView.findViewById(R.id.notesTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_logs, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = logs[position]
        holder.moodTextView.text = log.mood
        holder.notesTextView.text = log.notes
        holder.itemView.setOnClickListener { onClick(log) }

        holder.itemView.setOnLongClickListener {
            deleteMoodLog(log)

            logs.removeAt(position)
            notifyItemRemoved(position)
            true
        }
    }

    override fun getItemCount(): Int = logs.size

    private fun deleteMoodLog(log: DisplayMoodLog) {
        val dbHelper = MoodDatabaseHelper(context)
        dbHelper.deleteMood(log.mood, log.notes)
    }
}
