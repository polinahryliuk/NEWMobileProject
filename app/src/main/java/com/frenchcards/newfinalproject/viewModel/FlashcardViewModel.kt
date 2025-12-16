package com.frenchcards.newfinalproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frenchcards.newfinalproject.data.Flashcard
import com.frenchcards.newfinalproject.data.FlashcardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

    class FlashcardViewModel(
        private val repository: FlashcardRepository
    ) : ViewModel() {

        private val _cards = MutableStateFlow<List<Flashcard>>(emptyList())
        val cards: StateFlow<List<Flashcard>> = _cards.asStateFlow()

        private val _currentCardIndex = MutableStateFlow(0)
        val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

        private val _isCardFlipped = MutableStateFlow(false)
        val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

        init {
            loadAllCards()
        }
        private fun loadAllCards() {
            viewModelScope.launch {
                repository.allCards.collect { cardsList ->
                    _cards.value = cardsList
                }
            }
        }
        fun addFlashcard(french: String, english: String, category: String = "General") {
            viewModelScope.launch(Dispatchers.IO) {
                val card = Flashcard(
                    frenchWord = french.trim(),
                    englishTranslation = english.trim(),
                    category = category
                )
                repository.insert(card)
            }
        }
        fun flipCard() {
            _isCardFlipped.value = !_isCardFlipped.value
        }
        fun nextCard() {
            if (_currentCardIndex.value < _cards.value.size - 1) {
                _currentCardIndex.value++
                _isCardFlipped.value = false
            }
        }
        fun previousCard() {
            if (_currentCardIndex.value > 0) {
                _currentCardIndex.value--
                _isCardFlipped.value = false
            }
        }
        fun updateCard(card: Flashcard) {
            viewModelScope.launch {
                repository.update(card)

            }
        }
        fun deleteCard(card: Flashcard) {
            viewModelScope.launch {
                repository.delete(card)
            }
        }
    }
