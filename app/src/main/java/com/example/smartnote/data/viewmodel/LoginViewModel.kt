package com.example.smartnote.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnote.User
import com.example.smartnote.data.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun loginUser(
        username: String,
        password: String,
        onLoginSuccess: (String) -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val user = userRepository.getUserByUsername(username)
            if (user != null && user.password == password) {
                _currentUser.value = user
                onLoginSuccess(user.username)
            } else {
                onLoginFailure("Неверный логин или пароль")
            }
        }
    }
}
