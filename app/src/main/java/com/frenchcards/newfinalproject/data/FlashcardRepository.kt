package com.frenchcards.newfinalproject.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlashcardRepository @Inject constructor(
    private val flashcardDao: FlashcardDao
) {
    val allCards: Flow<List<Flashcard>> = flashcardDao.getAllCards()
    val totalCount: Flow<Int> = flashcardDao.getTotalCount()
    val masteredCount: Flow<Int> = flashcardDao.getMasteredCount()
    val categories: Flow<List<String>> = flashcardDao.getAllCategories()

    suspend fun getCardById(id: Int): Flashcard? = flashcardDao.getCardById(id)

    suspend fun insert(card: Flashcard): Long = flashcardDao.insert(card)

    suspend fun update(card: Flashcard): Int = flashcardDao.update(card)

    suspend fun delete(card: Flashcard): Int = flashcardDao.delete(card)

    suspend fun deleteAll(): Unit = flashcardDao.deleteAll()

    fun getCardsByCategory(category: String): Flow<List<Flashcard>> =
        flashcardDao.getCardsByCategory(category)
}