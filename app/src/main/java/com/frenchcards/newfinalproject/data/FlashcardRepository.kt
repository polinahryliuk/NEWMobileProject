package com.frenchcards.newfinalproject.data

import kotlinx.coroutines.flow.Flow

class FlashcardRepository(
    private val flashcardDao: FlashcardDao
) {
    val allCards: Flow<List<Flashcard>> = flashcardDao.getAllCards()
    val totalCount: Flow<Int> = flashcardDao.getTotalCount()
    val categories: Flow<List<String>> = flashcardDao.getAllCategories()

    suspend fun insert(card: Flashcard): Long = flashcardDao.insert(card)

    suspend fun update(card: Flashcard): Int = flashcardDao.update(card)

    suspend fun delete(card: Flashcard): Int = flashcardDao.delete(card)

}