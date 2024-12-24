package com.example.lojasocial.domain.use_case

import android.net.Uri
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.User
import kotlinx.coroutines.flow.Flow


class UserUseCase(private val repository: UserRepositoryImpl) {

    suspend fun createUser(user: User): User?{
        val id = repository.createUser(user.toUserDto())
        return id?.let {userId ->
            return user.copy(userId)
        }
    }

    suspend fun updateUser(user: User): User?{
        if(repository.updateUser(user.toUserDto(), user.userId))
            return user
        else
            return null
    }


    suspend fun getUserByEmail(email: String): User?{
        return repository.getUserByEmail(email)
    }

    fun observeUsers(): Flow<List<User>> {
        return repository.observeUsers()
    }

    fun getUserById(userId: String): Flow<User?>{
        return repository.getUserById(userId)
    }


    suspend fun uploadUserImage(userId: String, imageUri: Uri): Boolean{
        val imageUrl = repository.uploadUserImage(userId, imageUri) ?: return false
        return repository.updateUserImage(userId, imageUrl)
    }


}