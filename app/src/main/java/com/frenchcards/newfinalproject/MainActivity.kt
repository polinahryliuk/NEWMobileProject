package com.frenchcards.newfinalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.frenchcards.newfinalproject.App
import com.frenchcards.newfinalproject.data.FlashcardDatabase
import com.frenchcards.newfinalproject.ui.theme.NEWFinalProjectTheme
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
            NEWFinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    ) {
                    App()
                }
            }
        }
    }
}