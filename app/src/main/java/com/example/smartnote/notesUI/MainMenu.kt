package com.example.smartnote.notesUI

import android.util.Log
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartnote.R
import com.example.smartnote.data.viewmodel.LoginViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1F1F1F),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color.Gray
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color.DarkGray
)

// Custom Theme Composable
@Composable
fun SmartNoteTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = colors.background)
    }

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(
    username: String,
    onAddNoteClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavHostController
) {
    var isDarkMode by remember { mutableStateOf(false) } // Теперь тут управляем темой
    var currentGroup by remember { mutableStateOf("Закреплено") }
    var searchQuery by remember { mutableStateOf("") }


    // Оборачиваем всё в SmartNoteTheme, чтобы переключение темы работало глобально
    SmartNoteTheme(darkTheme = isDarkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Приветствие
                Text(
                    text = "Привет, $username!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Поисковая строка с лупой, микрофоном и переключением темы
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { query -> searchQuery = query },
                        placeholder = { Text("Поиск", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { /* Логика микрофона (пока не реализована) */ }) {
                                Image(
                                    painter = painterResource(
                                        id = if (isDarkMode) R.drawable.micro_gray else R.drawable.mirco
                                    ),
                                    contentDescription = "Микрофон",
                                    Modifier.size(24.dp)
                                )
                            }
                        }
                    )

                    // Кнопка переключения темы
                    IconButton(onClick = { isDarkMode = !isDarkMode }) {
                        Text(
                            if (isDarkMode) "☀️" else "🌙",
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }

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
                                contentColor = if (currentGroup == group) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text(group)
                        }
                    }
                }

                // Spacer() для размещения NavigationBar внизу экрана
                Spacer(modifier = Modifier.weight(1f))

                // BottomNavigation
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        icon = { Image(
                            painter = painterResource(
                                id = if (isDarkMode) R.drawable.archive_white else R.drawable.archive
                            ),
                            contentDescription = "Архив",
                            Modifier.size(30.dp)
                        ) },
                        label = { Text("Архив") },
                        selected = false,
                        onClick = { /* Добавьте экран настроек, если нужно */ },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    NavigationBarItem(
                        icon = {
                            Image(
                                painter = painterResource(
                                    id = if (isDarkMode) R.drawable.bottom_note_gray else R.drawable.bottom_note
                                ),
                                contentDescription = "Заметки",
                                Modifier.size(30.dp)
                            )
                        },
                        label = { Text("Заметки") },
                        selected = currentRoute?.startsWith("main") == true,
                        onClick = {
                            // Переход на экран заметок
                            Log.d("NAVIGATION", "Переход к main/$username")
                            navController.navigate("main/$username") {
                                popUpTo("main/$username") { inclusive = true }
                            }
                                  },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Настройки") },
                        label = { Text("Настройки") },
                        selected = currentRoute == "profile",
                        onClick = { Log.d("NAVIGATION", "Переход к profile/$username")
                            navController.navigate("profile/$username")
                        }  ,
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}






