package com.example.lojasocial.domain.use_case

import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {
    fun isLoggedIn(): Boolean{
        if(repository.currentUser != null)
            return true
        else
            return false
    }

    suspend fun login(email: String, password: String):User?{
        if(email.isEmpty() || password.isEmpty())
            throw IllegalArgumentException("Email or password empty!")
        return repository.login(email,password)
    }

    suspend fun register(email: String, password: String):User?{
        if(email.isEmpty() || password.isEmpty())
            throw IllegalArgumentException("Email or password empty!")
        return repository.register(email,password)
    }

    suspend fun logout(){
        repository.logout()
    }
}