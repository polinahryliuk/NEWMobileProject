package com.frenchcards.newfinalproject.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModel
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ReviewScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as android.app.Application
    val viewModel: FlashcardViewModel = viewModel(
        factory = FlashcardViewModelFactory(application)
    )

        val cards by viewModel.cards.collectAsState()
        val currentIndex by viewModel.currentCardIndex.collectAsState()
        val isFlipped by viewModel.isCardFlipped.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var editFrench by remember { mutableStateOf("") }
    var editEnglish by remember { mutableStateOf("") }
    var editCategory by remember { mutableStateOf("General") }

    if (showEditDialog && cards.isNotEmpty()) {
        editFrench = cards[currentIndex].frenchWord
        editEnglish = cards[currentIndex].englishTranslation
        editCategory = cards[currentIndex].category
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Cards") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Card ${currentIndex + 1} of ${cards.size}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { viewModel.flipCard() },
                colors = CardDefaults.cardColors(
                    containerColor = if (cards.isNotEmpty() && cards[currentIndex].isMastered) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (cards.isEmpty()) {
                        Text("No cards to review")
                    } else {
                        val currentCard = cards[currentIndex]
                        if (isFlipped) {
                            Text(
                                currentCard.englishTranslation,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        } else {
                            Text(
                                currentCard.frenchWord,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
            Text(
                "Tap card to flip",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.previousCard() },
                    enabled = currentIndex > 0
                ) {
                    Icon(Icons.Filled.ArrowBack, "Previous")
                }
                Button(
                    onClick = { viewModel.nextCard() },
                    enabled = cards.isNotEmpty() && currentIndex < cards.size - 1
                ) {
                    Icon(Icons.Filled.ArrowForward, "Next")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = { showEditDialog = true },
                    enabled = cards.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Card")
                }
                Button(
                    onClick = { showDeleteDialog = true },
                    enabled = cards.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete Card")
                }
            }
        }
        if (showEditDialog && cards.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Edit Card") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = editFrench,
                            onValueChange = { editFrench = it },
                            label = { Text("French") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = editEnglish,
                            onValueChange = { editEnglish = it },
                            label = { Text("English") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = editCategory,
                            onValueChange = { editCategory = it },
                            label = { Text("Category") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val updatedCard = cards[currentIndex].copy(
                                frenchWord = editFrench,
                                englishTranslation = editEnglish,
                                category = editCategory
                            )
                            viewModel.updateCard(updatedCard)
                            showEditDialog = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showEditDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
        if (showDeleteDialog && cards.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Card") },
                text = { Text("Are you sure you want to delete this card?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteCard(cards[currentIndex])
                            showDeleteDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}