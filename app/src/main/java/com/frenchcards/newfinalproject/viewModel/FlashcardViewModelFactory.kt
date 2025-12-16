package com.frenchcards.newfinalproject.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frenchcards.newfinalproject.data.FlashcardDatabase
import com.frenchcards.newfinalproject.data.FlashcardRepository

class FlashcardViewModelFactory(
    val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            val database = FlashcardDatabase.getDatabase(application)
            val repository = FlashcardRepository(database.flashcardDao())
            return FlashcardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
