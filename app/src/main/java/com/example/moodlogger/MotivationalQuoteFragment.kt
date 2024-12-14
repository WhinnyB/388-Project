package com.example.moodlogger

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MotivationalQuoteFragment : Fragment(R.layout.fragment_motivational_quote) {

    lateinit var quoteTextView: TextView
    private lateinit var refreshButton: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteTextView = view.findViewById(R.id.quoteTextView)
        refreshButton = view.findViewById(R.id.refreshButton)

        fetchQuote()

        refreshButton.setOnClickListener {
            fetchQuote()
        }
    }

    private fun fetchQuote() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ZenQuotesService::class.java)

        service.getQuote().enqueue(object : Callback<List<QuoteResponse>> {
            override fun onResponse(
                call: Call<List<QuoteResponse>>,
                response: Response<List<QuoteResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val quote = response.body()!![0]
                    quoteTextView.text = "\"${quote.q}\" - ${quote.a}"
                } else {
                    quoteTextView.text = "Failed to load quote."
                }
            }

            override fun onFailure(call: Call<List<QuoteResponse>>, t: Throwable) {
                quoteTextView.text = "Error: ${t.message}"
            }
        })
    }

}
