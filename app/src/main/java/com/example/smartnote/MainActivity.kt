package com.example.smartnote

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartnote.data.user.UserRepository
import com.example.smartnote.notesUI.CreateNoteActivity
import com.example.smartnote.notesUI.MainMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLog = remember { mutableStateOf(false) }
            val isRegistering = remember { mutableStateOf(false) }
            val errorMessage = remember { mutableStateOf<String?>(null) }

            val db = AppDatabase.getInstance(this)
            val userDao = db.userDao()
            val userRepository = UserRepository.getInstance(userDao)

            val navController: NavHostController = rememberNavController()

            NavHost(navController = navController, startDestination = "crossfade") {
                composable("crossfade") {
                    Crossfade(
                        targetState = when {
                            isLog.value -> "MainMenu"
                            isRegistering.value -> "Registering"
                            else -> "Login"
                        }
                    ) { screen ->
                        when (screen) {
                            "MainMenu" -> MainMenu(onAddNoteClick = { openCreateNoteActivity() })
                            "Login" -> LoginScreenAndroid(
                                onLoginSuccess = { isLog.value = true },
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