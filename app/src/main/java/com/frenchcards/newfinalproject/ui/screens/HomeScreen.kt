package com.frenchcards.newfinalproject.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
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
            // Quick Stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ready to learn?", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("25 cards to review today", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
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

            // Recent Cards List
            Text(
                "Recent Cards",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
            )

            // Placeholder for card list
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