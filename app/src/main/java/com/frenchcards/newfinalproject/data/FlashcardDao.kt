package com.frenchcards.newfinalproject.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Insert
    suspend fun insert(flashcard: Flashcard): Long

    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    fun getAllCards(): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE id = :cardId")
    suspend fun getCardById(cardId: Int): Flashcard?

    @Update
    suspend fun update(flashcard: Flashcard): Int

    @Delete
    suspend fun delete(flashcard: Flashcard): Int

    @Query("DELETE FROM flashcards")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM flashcards")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM flashcards WHERE isMastered = 1")
    fun getMasteredCount(): Flow<Int>

    @Query("SELECT * FROM flashcards WHERE category = :category")
    fun getCardsByCategory(category: String): Flow<List<Flashcard>>

    @Query("SELECT DISTINCT category FROM flashcards")
    fun getAllCategories(): Flow<List<String>>
}