package com.frenchcards.newfinalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModel
import com.frenchcards.newfinalproject.viewModel.FlashcardViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as android.app.Application
    val viewModel: FlashcardViewModel = viewModel(
        factory = FlashcardViewModelFactory(application)
    )
    var frenchWord by remember { mutableStateOf("") }
    var englishWord by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add French Word") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
                Text("Add New Card", style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = frenchWord,
                onValueChange = { frenchWord = it },
                label = { Text("French Word") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = englishWord,
                onValueChange = { englishWord = it },
                label = { Text("English Word") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (frenchWord.isNotBlank() && englishWord.isNotBlank()) {
                        viewModel.addFlashcard(frenchWord, englishWord)
                        frenchWord = ""
                        englishWord = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = frenchWord.isNotBlank() && englishWord.isNotBlank()
            ) {
                Text("Save Card")
            }
        }
    }
}