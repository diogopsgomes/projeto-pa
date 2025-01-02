package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.RoleDto
import com.example.lojasocial.domain.model.Role
import com.example.lojasocial.domain.repository.RoleRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RoleRepositoryImpl: RoleRepository {
    private val db = Firebase.firestore


    override suspend fun getRoleById(roleId: String): Role? {
        try{
            val documentRef = db.collection("Role")
                .document(roleId)
                .get()
                .await()

            return documentRef.toObject(RoleDto::class.java)?.toRole(documentRef.id)
        }catch (ex: Exception){
            Log.e("Firestore", "Erro ao procurar Role", ex)
            return null
        }
    }

    override suspend fun getAllRoles(): List<Role> {
        try {
            val rolesRef = db.collection("Role").get().await()
            return rolesRef.documents.mapNotNull { document ->
                document.toObject(RoleDto::class.java)?.toRole(document.id)
            }
        } catch (ex: Exception) {
            Log.e("Firestore", "Erro ao buscar todas as Roles", ex)
            return emptyList()
        }
    }




}