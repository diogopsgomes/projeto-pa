package com.example.lojasocial.data.repository

import com.example.lojasocial.data.remote.model.UserAuthDto
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.data.remote.model.UserDto
import com.example.lojasocial.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthRepositoryImpl(private val auth: FirebaseAuth): AuthRepository {
    override val currentUser = auth.currentUser

    override suspend fun register(email: String, password: String): User? {
        return try {
            val userDto = suspendCoroutine<UserAuthDto?> { continuation ->
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                continuation.resume(UserAuthDto(currentUser.email.toString()))
                            } else {
                                continuation.resumeWithException(IllegalStateException("User not found after registration"))
                            }
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception("Registration failed"))
                        }
                    }
            }
            userDto?.toUser()
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message ?: "Erro no Registro")
        }
    }


    override suspend fun login(email: String, password: String): User? {
        return try {
            val userDto = suspendCoroutine<UserAuthDto?> { continuation ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                continuation.resume(UserAuthDto(currentUser.email.toString()))
                            } else {
                                continuation.resumeWithException(IllegalStateException("User not found after login"))
                            }
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception("Login failed"))
                        }
                    }
            }
            userDto?.toUser()
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message ?: "Erro no Login")
        }
    }


    override fun logout(){
        auth.signOut()
    }
}