package com.example.lojasocial.data.repository

import android.net.Uri
import android.util.Log
import com.example.lojasocial.data.remote.model.UserDto
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


class UserRepositoryImpl: UserRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val storage:FirebaseStorage = FirebaseStorage.getInstance()

    override suspend fun createUser(user: UserDto): String?{
        val utilizadoresRef = db.collection("User")
        try {
            val document = utilizadoresRef.add(user).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar utilizador", e)
            return null
        }
    }

    override suspend fun updateUser(user: UserDto, userId: String): Boolean{
        val utilizadoresRef = db.collection("User")
        try {
            val document = utilizadoresRef.document(userId)

            document.set(user).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao atualizar utilizador", e)
            return false
        }
    }

    override fun getUserById(userId: String): Flow<User?> {
        return flow {
            var document = db.collection("User").document(userId)
            val user = document.get().await().toObject(UserDto::class.java)?.toUser(document.id)
            emit(user)
        }
    }

    override suspend fun getUserByEmail(email: String): User?{
        val utilizadoresRef = db.collection("User")
        try {
            val document = utilizadoresRef.whereEqualTo("email", email).get().await()
            val userDto = document.firstOrNull()?.toObject(UserDto::class.java)
            return userDto?.toUser(document.documents.firstOrNull()!!.id)
        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar utilizador", e)
            return null
        }
    }

    //Retorna Flow<Users>
    override fun observeUsers() =
        db.collection("User")
            .snapshots().map { snapshot ->
                snapshot.map {document ->
                    document.toObject(UserDto::class.java).toUser(document.id)
                }
            }.filterNotNull()

    override suspend fun uploadUserImage(userId: String, imageUri: Uri): String?{
        return try {
            val imageRef = storage.reference.child("user_images/$userId.jpg")
            imageRef.putFile(imageUri).await()
            imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error uploading user image", e)
            null
        }
    }

    override suspend fun updateUserImage(userId: String, imageUrl: String): Boolean {
        return try {
            db.collection("User").document(userId)
                .update("picture", imageUrl).await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error updating user image", e)
            false
        }
    }

}