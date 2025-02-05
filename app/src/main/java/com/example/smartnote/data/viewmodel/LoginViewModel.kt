package com.example.smartnote.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartnote.data.user.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun loginUser(username: String, password: String, onLoginSuccess: () -> Unit, onLoginFailure: (String) -> Unit) {
        val user = userRepository.getUserByUsername(username)
        if (user != null) {
            onLoginSuccess()
        } else {
            onLoginFailure("Неверный логин или пароль")

        }
    }
}