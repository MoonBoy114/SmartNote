package com.example.smartnote.data.user

import com.example.smartnote.User

class RegistrationRepository(private val userDao: UserDao) {
    suspend fun register(username: String, email: String, password: String): Result<Unit> {
        return try {
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                return Result.failure(Exception("Пользователь уже существует"))
            }
            val newUser = User(username = username, email = email, password = password)
            userDao.insertUser(newUser)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
