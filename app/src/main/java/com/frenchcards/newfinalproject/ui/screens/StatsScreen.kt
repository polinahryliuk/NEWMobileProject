package com.frenchcards.newfinalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun StatsScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as android.app.Application
    val viewModel: FlashcardViewModel = viewModel(
        factory = FlashcardViewModelFactory(application)
    )
    val cards by viewModel.cards.collectAsState()
    val totalCards = cards.size
    val categoryCounts = mutableMapOf<String, Int>()
    for (card in cards) {
        val category = card.category
        categoryCounts[category] = categoryCounts.getOrDefault(category, 0) + 1
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistics") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            StatCard("Total Cards", totalCards.toString())

            if (categoryCounts.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Cards by Category",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        categoryCounts.forEach { (category, count) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(category)
                                Text("$count cards")
                            }
                        }
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        if (totalCards == 0) "No cards yet" else "Your cards",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        if (totalCards == 0)
                            "Add some flashcards to get started"
                        else
                            "You have $totalCards cards in ${categoryCounts.size} categories",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun StatCard(
    title: String,
    value: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    value,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    title,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}