package com.example.lojasocial.domain.repository

import android.net.Uri
import com.example.lojasocial.data.remote.model.UserDto
import com.example.lojasocial.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: UserDto): String?
    suspend fun updateUser(user: UserDto, userId: String): Boolean
    fun getUserById(userId: String): Flow<User?>
    suspend fun getUserByEmail(email: String): User?
    fun observeUsers(): Flow<List<User>>
    suspend fun uploadUserImage(userId: String, imageUri: Uri): String?
    suspend fun updateUserImage(userId: String, imageUrl: String): Boolean
}