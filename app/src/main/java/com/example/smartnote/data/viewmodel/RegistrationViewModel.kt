package com.example.smartnote.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnote.User
import com.example.smartnote.data.user.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(username: String,  email: String,password: String) {
        viewModelScope.launch {
            val Password = password
            val user = User(username = username, email = email, password = password)
            userRepository.registerUser(user)
        }
    }

}