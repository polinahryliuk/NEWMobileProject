package com.frenchcards.newfinalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import com.frenchcards.newfinalproject.App
import com.frenchcards.newfinalproject.data.FlashcardDatabase
import com.frenchcards.newfinalproject.workers.scheduleDailyReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleDailyReminder(this)
        lifecycleScope.launch(Dispatchers.IO) {
            FlashcardDatabase.getDatabase(applicationContext)
        }
        setContent {
            MaterialTheme {
                Surface {
                    App()
                }
            }
        }
    }
}