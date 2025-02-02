package com.example.smartnote.data.user

import com.example.smartnote.User


class UserRepository(private val userDao: UserDao) {
    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(userDao: UserDao): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(userDao)
                INSTANCE = instance
                instance
            }
        }


    }
    suspend fun registerUser(user: User) = userDao.insertUser(user)
    suspend fun getUserByUsername(username: String) = userDao.getUserByUsername(username)

}