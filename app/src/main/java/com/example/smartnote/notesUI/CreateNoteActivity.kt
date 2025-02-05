package com.example.smartnote.notesUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartnote.ui.theme.SmartNoteTheme

class CreateNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartNoteTheme {
                CreateNoteScreen { finish() }
            }
        }
    }
}

@Composable
fun CreateNoteScreen(onBack: () -> Unit) {
    var noteText by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите заметку...") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {  }) {
                Text("Сохранить")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}

@Preview
@Composable
fun PreviewCreateNoteScreen() {
    CreateNoteScreen(onBack = {})
}
