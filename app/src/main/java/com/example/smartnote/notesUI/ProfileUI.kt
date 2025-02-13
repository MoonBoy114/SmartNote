package com.example.smartnote.notesUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartnote.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(username: String, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль $username") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {  // Возврат назад
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->  // Передаем padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Используем padding от Scaffold
                .padding(16.dp),  // Добавляем внутренний отступ
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Привет, $username!",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Это экран профиля пользователя.")
        }
    }
}

