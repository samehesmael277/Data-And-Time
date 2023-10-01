package com.sameh.dataandtime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sameh.dataandtime.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var updateTimeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        startGetDateTime()
    }

    private fun startGetDateTime() {
        updateTimeJob = lifecycleScope.launch {
            while (true) {
                withContext(Dispatchers.Main) {
                    getDateTime()
                    delay(500)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        updateTimeJob?.cancel()
    }

    private fun getDateTime() {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val currentDate = dateFormat.format(Date()).uppercase(Locale.getDefault())
        val currentTime = timeFormat.format(Date())
        updateDateAndTime(currentDate, currentTime)
    }

    private fun updateDateAndTime(date: String, time: String) {
        binding.tvDate.text = date
        binding.tvTime.text = time
    }

    override fun onDestroy() {
        super.onDestroy()

        updateTimeJob = null
    }
}