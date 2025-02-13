package com.example.smartnote

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartnote.data.user.UserRepository
import com.example.smartnote.data.viewmodel.LoginViewModel
import com.example.smartnote.notesUI.CreateNoteActivity
import com.example.smartnote.notesUI.MainMenu
import com.example.smartnote.notesUI.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLog = remember { mutableStateOf("") }
            val isRegistering = remember { mutableStateOf(false) }

            val db = AppDatabase.getInstance(this)
            val userDao = db.userDao()
            val userRepository = UserRepository.getInstance(userDao)

            val navController: NavHostController = rememberNavController()

            val username = remember { mutableStateOf("") }

            NavHost(navController = navController, startDestination = "crossfade") {
                composable("crossfade") {
                    Crossfade(
                        targetState = when {
                            isLog.value.isNotEmpty() -> "main/${username.value}" // Проверяем, не пустой ли username
                            isRegistering.value -> "Registering"
                            else -> "Login"
                        }
                    ) { screen ->
                        when (screen) {
                            "Login" -> LoginScreenAndroid(
                                onLoginSuccess = { user ->
                                    username.value = user  // Сохраняем username после входа
                                    navController.navigate("main/${user}")
                                },
                                onRegisterClick = { isRegistering.value = true },
                                userRepository = userRepository
                            )
                            "Registering" -> RegistrationScreen(
                                onBackClick = { isRegistering.value = false },
                                userRepository = userRepository
                            )
                        }
                    }
                }
                composable("main/{username}") { backStackEntry ->
                    val user = backStackEntry.arguments?.getString("username") ?: ""

                    MainMenu(
                        username = user,  // Передаём username в MainMenu
                        onAddNoteClick = { openCreateNoteActivity() },
                        onProfileClick = {
                            navController.navigate("profile/$user")  // Переход в профиль
                        },
                        navController = navController
                    )
                }
                // Добавляем маршрут для ProfileScreen
                composable("profile/{username}") { backStackEntry ->
                    val user = backStackEntry.arguments?.getString("username") ?: ""
                    ProfileScreen(username = user, navController = navController)
                }

            }
        }
        setupAppShortcuts()
    }



    private fun openCreateNoteActivity() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    private fun setupAppShortcuts() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val shortcut = ShortcutInfo.Builder(this, "create_note")
            .setShortLabel("Создать заметку")
            .setLongLabel("Создать новую заметку")
            .setIcon(Icon.createWithResource(this, R.drawable.notes))
            .setIntent(Intent(this, CreateNoteActivity::class.java).setAction(Intent.ACTION_VIEW))
            .build()

        shortcutManager.dynamicShortcuts = listOf(shortcut)
    }
}