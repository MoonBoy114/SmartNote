package com.example.smartnote

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartnote.notesUI.MainMenu
import com.example.smartnote.ui.theme.SmartNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartNoteTheme {
                MainMenu { openCreateNoteActivity() }
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