package com.example.lojasocial.domain.repository

import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): User?
    suspend fun register(email: String, password: String): User?
    fun logout()
}