package com.example.smartnote.notesUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartnote.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(onAddNoteClick: () -> Unit) {
    var isDarkMode by remember { mutableStateOf(false) }
    var currentGroup by remember { mutableStateOf("Закреплено") }
    var searchQuery by remember { mutableStateOf("") }

    val backgroundColor = if (isDarkMode) Color(0xFF000000) else Color(0xFFF5F5F5)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val placeholderColor = if (isDarkMode) Color.Gray else Color.DarkGray
    val containerColor = if (isDarkMode) Color(0xFF1C1C1E) else Color(0xFFABB0A4)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Заметки",
                        style = MaterialTheme.typography.titleLarge,
                        color = textColor
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkMode) Color.Black else Color.White
                ),
                actions = {
                    IconButton(onClick = { isDarkMode = !isDarkMode }) {
                        Text(if (isDarkMode) "☀️" else "🌙", color = textColor, textAlign = TextAlign.Center)
                    }
                }
            )

            // Поисковая строка
            TextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                placeholder = { Text("Поиск", color = placeholderColor) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = containerColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = placeholderColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = placeholderColor)
                },
                trailingIcon = {
                    IconButton(onClick = { /* Логика микрофона (пока не реализована) */ }) {
                        Image(
                            painterResource(R.drawable.mirco), contentDescription = "Micro", Modifier.size(20.dp)
                        )
                    }
                }
            )

            // Меню групп
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Закреплено", "Предыдущие 7 дней", "Предыдущие 30 дней").forEach { group ->
                    TextButton(
                        onClick = { currentGroup = group },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (currentGroup == group) Color(0xFF000000) else textColor
                        )
                    ) {
                        Text(group)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Группа: $currentGroup",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onAddNoteClick) {
                    Text("Добавить заметку", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainMenu() {
    MainMenu(onAddNoteClick = {})
}

