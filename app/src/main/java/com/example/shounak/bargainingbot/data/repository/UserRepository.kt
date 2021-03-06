package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * User repository interface
 */
interface UserRepository {
    suspend fun getCurrentUser(): LiveData<User>


    suspend fun getCurrentUserLocal(): User?
    suspend fun clearUser()
}