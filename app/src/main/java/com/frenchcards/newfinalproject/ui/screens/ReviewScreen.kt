package com.frenchcards.newfinalproject.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: FlashcardViewModel = viewModel()
) {
    val cards by viewModel.cards.collectAsState()
    val currentCardIndex by viewModel.currentCardIndex.collectAsState()
    val isCardFlipped by viewModel.isCardFlipped.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

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
            // Progress indicator
            LinearProgressIndicator(
                progress = if (uiState.cards.isNotEmpty()) {
                    uiState.currentCardIndex.toFloat() / uiState.cards.size
                } else 0f,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Card ${uiState.currentCardIndex + 1} of ${uiState.cards.size}",
                style = MaterialTheme.typography.labelLarge
            )

            // Flashcard with flip animation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                val rotation = animateFloatAsState(
                    targetValue = if (uiState.isFlipped) 180f else 0f,
                    animationSpec = tween(500)
                )

                val frontAlpha = animateFloatAsState(
                    targetValue = if (uiState.isFlipped) 0f else 1f
                )

                val backAlpha = animateFloatAsState(
                    targetValue = if (uiState.isFlipped) 1f else 0f
                )

                // Front of card
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = rotation.value
                            cameraDistance = 8 * density
                        }
                        .alpha(frontAlpha.value)
                        .clickable { viewModel.flipCard() },
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.cards.isNotEmpty()) {
                            Text(
                                uiState.cards[uiState.currentCardIndex].frenchWord,
                                style = MaterialTheme.typography.displayMedium
                            )
                        } else {
                            Text("No cards to review", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                // Back of card
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = rotation.value + 180f
                            cameraDistance = 8 * density
                        }
                        .alpha(backAlpha.value)
                        .clickable { viewModel.flipCard() },
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.cards.isNotEmpty()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    uiState.cards[uiState.currentCardIndex].englishTranslation,
                                    style = MaterialTheme.typography.displayMedium
                                )
                                Text(
                                    "(Click to flip back)",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            Text(
                "Tap card to flip",
                style = MaterialTheme.typography.bodySmall
            )

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { viewModel.previousCard() },
                    enabled = uiState.currentCardIndex > 0
                ) {
                    Icon(Icons.Filled.ArrowBack, "Previous")
                }

                // Mastery buttons
                Button(
                    onClick = { /* Mark as difficult */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Filled.Close, "Hard")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hard")
                }

                Button(
                    onClick = { /* Mark as mastered */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Filled.Check, "Easy")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Easy")
                }

                IconButton(
                    onClick = { viewModel.nextCard() },
                    enabled = uiState.currentCardIndex < uiState.cards.size - 1
                ) {
                    Icon(Icons.Filled.ArrowForward, "Next")
                }
            }
        }
    }
}