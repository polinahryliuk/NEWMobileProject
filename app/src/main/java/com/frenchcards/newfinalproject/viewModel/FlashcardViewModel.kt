package com.frenchcards.newfinalproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frenchcards.newfinalproject.data.Flashcard
import com.frenchcards.newfinalproject.data.FlashcardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardViewModel @Inject constructor(
    private val repository: FlashcardRepository
) : ViewModel() {

    @HiltViewModel
    class FlashcardViewModel @Inject constructor(
        private val repository: FlashcardRepository
    ) : ViewModel() {

        // State for all flashcards
        private val _cards = MutableStateFlow<List<Flashcard>>(emptyList())
        val cards: StateFlow<List<Flashcard>> = _cards.asStateFlow()

        // Review-specific state
        private val _currentCardIndex = MutableStateFlow(0)
        val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

        private val _isCardFlipped = MutableStateFlow(false)
        val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

        // UI state
        private val _uiState = MutableStateFlow<FlashcardUiState>(FlashcardUiState.Loading)
        val uiState: StateFlow<FlashcardUiState> = _uiState.asStateFlow()

        init {
            loadAllCards()
        }

        private fun loadAllCards() {
            viewModelScope.launch {
                repository.allCards.collect { cardsList ->
                    _cards.value = cardsList
                    _uiState.value = FlashcardUiState.Success(cardsList)
                }
            }
        }

        fun addFlashcard(french: String, english: String, category: String = "General") {
            viewModelScope.launch {
                try {
                    val card = Flashcard(
                        frenchWord = french.trim(),
                        englishTranslation = english.trim(),
                        category = category
                    )
                    repository.insert(card)
                } catch (e: Exception) {
                    _uiState.value = FlashcardUiState.Error("Failed to add card: ${e.message}")
                }
            }
        }
        // Review screen methods
        fun flipCard() {
            _isCardFlipped.value = !_isCardFlipped.value
        }

        fun nextCard() {
            val currentIndex = _currentCardIndex.value
            val totalCards = _cards.value.size

            if (currentIndex < totalCards - 1) {
                _currentCardIndex.value = currentIndex + 1
                _isCardFlipped.value = false  // Reset flip when changing cards
            }
        }

        fun previousCard() {
            val currentIndex = _currentCardIndex.value

            if (currentIndex > 0) {
                _currentCardIndex.value = currentIndex - 1
                _isCardFlipped.value = false  // Reset flip when changing cards
            }
        }

        fun markCardAsMastered() {
            viewModelScope.launch {
                val currentIndex = _currentCardIndex.value
                val currentCards = _cards.value

                if (currentIndex < currentCards.size) {
                    val card = currentCards[currentIndex]
                    val updatedCard = card.copy(isMastered = true)
                    repository.update(updatedCard)
                }
            }
        }

        fun updateCard(card: Flashcard) {
            viewModelScope.launch {
                try {
                    repository.update(card)
                } catch (e: Exception) {
                    _uiState.value = FlashcardUiState.Error("Failed to update card: ${e.message}")
                }
            }
        }

        fun deleteCard(card: Flashcard) {
            viewModelScope.launch {
                try {
                    repository.delete(card)
                } catch (e: Exception) {
                    _uiState.value = FlashcardUiState.Error("Failed to delete card: ${e.message}")
                }
            }
        }

        fun toggleMastered(cardId: Int) {
            viewModelScope.launch {
                val currentCards = _cards.value
                val cardToUpdate = currentCards.find { it.id == cardId }
                cardToUpdate?.let { card ->
                    val updatedCard = card.copy(isMastered = !card.isMastered)
                    updateCard(updatedCard)
                }
            }
        }
    }

    // UI State sealed class for better state management
    sealed class FlashcardUiState {
        object Loading : FlashcardUiState()
        object Empty : FlashcardUiState()
        data class Success(val cards: List<Flashcard>) : FlashcardUiState()
        data class Error(val message: String) : FlashcardUiState()
    }
}