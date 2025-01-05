package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.ActivityDto
import com.example.lojasocial.data.remote.model.MemberDto
import com.example.lojasocial.data.remote.model.UserDto
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.repository.ActivityRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ActivityRepositoryImpl: ActivityRepository {
    private val db = Firebase.firestore

    override suspend fun createActivity(activity: ActivityDto): String?{
        val utilizadoresRef = db.collection("Activity")
        try {
            val document = utilizadoresRef.add(activity).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar Atividade", e)
            return null
        }
    }

    override suspend fun updateActivity(activity: ActivityDto, activityId: String): Boolean{
        val utilizadoresRef = db.collection("Activity")
        try {
            val document = utilizadoresRef.document(activityId)

            document.set(activity).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao atualizar atividade", e)
            return false
        }
    }


    override suspend fun getActivityById(activityId: String): Activity? {
        val ref = db.collection("Activity")
        try {
            val document = ref.document(activityId).get().await()

            val activityDto = document.toObject(ActivityDto::class.java)

            return activityDto?.toActivity(document.id)

        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar utilizador", e)
            return null
        }
    }

    override suspend fun addEnrolled(activityid: String): Boolean{
        try {
            val documentRef = db.collection("Activity").document(activityid)

            val documentSnapshot = documentRef.get().await()

            val quantidadeAtual = documentSnapshot.getLong("enrolled") ?: 0L

            documentRef.update("enrolled", quantidadeAtual + 1).await()

            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    override suspend fun removeEnrolled(activityid: String): Boolean {
        try {
            val documentRef = db.collection("Activity").document(activityid)

            val documentSnapshot = documentRef.get().await()

            val quantidadeAtual = documentSnapshot.getLong("enrolled") ?: 0L

            if (quantidadeAtual > 0) {
                documentRef.update("enrolled", quantidadeAtual - 1).await()
                return true
            } else {
                return false
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }



    override fun observeActivities() =
        db.collection("Activity")
            .snapshots().map { snapshot ->
                snapshot.map {document ->
                    document.toObject(ActivityDto::class.java).toActivity(document.id)
                }
            }.filterNotNull()





}