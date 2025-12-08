package com.frenchcards.newfinalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val frenchWord: String,
    val englishTranslation: String,
    val category: String = "General",
    val createdAt: Long = System.currentTimeMillis(),
    val isMastered: Boolean = false
)