package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.ActivityDto
import com.example.lojasocial.data.remote.model.UserActivityDto
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.model.UserActivity
import com.example.lojasocial.domain.repository.UserActivityRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UserActivityRepositoryImpl: UserActivityRepository {
    private val db = Firebase.firestore

    override suspend fun addUserToActivity(userActivity: UserActivityDto): String?{
        val utilizadoresRef = db.collection("UserActivity")
        try {
            val document = utilizadoresRef.add(userActivity).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar membro", e)
            return null
        }
    }

    override suspend fun removeUserOfActivity(userActivity: UserActivityDto): Boolean{
        val utilizadoresRef = db.collection("UserActivity")
        try {
            val document = utilizadoresRef
                .whereEqualTo("userId", userActivity.userId)
                .whereEqualTo("activityId", userActivity.activityId)
                .get()
                .await()
            Log.d("Activity", "act: ${userActivity.activityId}, usr: ${userActivity.userId}")

            if(!document.isEmpty){
                for(doc in document.documents){
                    utilizadoresRef.document(doc.id).delete().await()
                }
                return true
            }else{
                return false
            }
            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao remover activity member", e)
            return false
        }
    }



    override fun observeUserActivities() =
        db.collection("UserActivity")
            .snapshots().map { snapshot ->
                snapshot.map {document ->
                    document.toObject(UserActivityDto::class.java).toUserActivity(document.id)
                }
            }.filterNotNull()


}