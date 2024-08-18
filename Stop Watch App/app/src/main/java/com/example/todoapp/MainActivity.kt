package com.example.todoapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var isRunning = false
    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var elapsedTime = 0L

    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val time = elapsedTime + (currentTime - startTime)

            val minutes = (time / 1000) / 60
            val seconds = (time / 1000) % 60
            val milliseconds = (time % 1000) / 10

            tvTimer.text = String.format("%02d:%02d:%02d.%02d", minutes, seconds, milliseconds / 10, milliseconds % 10)

            handler.postDelayed(this, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            startTimer()
        }

        btnPause.setOnClickListener {
            pauseTimer()
        }

        btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis()
            handler.post(updateTimer)
            isRunning = true
        }
    }

    private fun pauseTimer() {
        if (isRunning) {
            elapsedTime += System.currentTimeMillis() - startTime
            handler.removeCallbacks(updateTimer)
            isRunning = false
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(updateTimer)
        isRunning = false
        elapsedTime = 0L
        tvTimer.text = "00:00:00.00"
    }
}
