package com.frenchcards.newfinalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    navController: NavController,
    viewModel: FlashcardViewModel = viewModel()
) {
    var frenchWord by remember { mutableStateOf("") }
    var englishTranslation by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Card") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = frenchWord,
                onValueChange = { frenchWord = it },
                label = { Text("French Word") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Bonjour") }
            )

            OutlinedTextField(
                value = englishTranslation,
                onValueChange = { englishTranslation = it },
                label = { Text("English Translation") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Hello") }
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Greetings") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (frenchWord.isNotBlank() && englishTranslation.isNotBlank()) {
                        viewModel.addFlashcard(frenchWord, englishTranslation)
                        frenchWord = ""
                        englishTranslation = ""
                        navController.popBackStack()  // Go back to home
                    }
                },
                modifier = Modifier.align(Alignment.End),
                enabled = frenchWord.isNotBlank() && englishTranslation.isNotBlank()
            ) {
                Text("Save Card")
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Example Cards:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Bonjour - Hello")
                    Text("• Merci - Thank you")
                    Text("• Au revoir - Goodbye")
                    Text("• S'il vous plaît - Please")
                }
            }
        }
    }
}