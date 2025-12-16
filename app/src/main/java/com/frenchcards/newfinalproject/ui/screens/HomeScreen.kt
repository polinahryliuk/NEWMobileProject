package com.frenchcards.newfinalproject.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
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
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as android.app.Application

    val viewModel: FlashcardViewModel = viewModel(
        factory = FlashcardViewModelFactory(application)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("French Flashcards") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_card") }
            ) {
                Icon(Icons.Filled.Add, "Add Card")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ready to learn?", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("3 cards to review today", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            ActionButton(
                icon = Icons.Filled.PlayArrow,
                text = "Start Review",
                onClick = { navController.navigate("review") }
            )

            ActionButton(
                icon = Icons.Filled.List,
                text = "View Statistics",
                onClick = { navController.navigate("stats") }
            )

            ActionButton(
                icon = Icons.Filled.Settings,
                text = "Settings",
                onClick = { navController.navigate("settings") }
            )

            Text(
                "Example of Cards",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
            )
            LazyColumn {
                items(listOf("Bonjour", "Merci", "Au revoir", "S'il vous plaît")) { word ->
                    ListItem(
                        headlineContent = { Text(word) },
                        supportingContent = { Text("Hello, Thank you, Goodbye, Please") }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}