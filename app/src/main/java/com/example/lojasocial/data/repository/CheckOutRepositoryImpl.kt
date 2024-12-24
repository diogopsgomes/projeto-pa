package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.CheckInDto
import com.example.lojasocial.data.remote.model.CheckOutDto
import com.example.lojasocial.data.remote.model.MemberDto
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.domain.model.CheckOut
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.repository.CheckOutRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CheckOutRepositoryImpl: CheckOutRepository {
    private val db = Firebase.firestore

    override suspend fun createCheckOut(checkOut: CheckOutDto): String?{
        val utilizadoresRef = db.collection("CheckOut")
        try {
            val document = utilizadoresRef.add(checkOut).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar CheckOut", e)
            return null
        }
    }

    override suspend fun updateCheckOut(checkOut: CheckOutDto, checkOutId: String): Boolean{
        val utilizadoresRef = db.collection("CheckOut")
        try {
            val document = utilizadoresRef.document(checkOutId)

            document.set(checkOut).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao ao atualizar CheckIn", e)
            return false
        }
    }

    override suspend fun getCheckOutById(checkOutId: String): CheckOut?{
        val utilizadoresRef = db.collection("CheckOut")
        try {
            val document = utilizadoresRef.document(checkOutId).get().await()

            val checkOutDto = document.toObject(CheckOutDto::class.java)

            return checkOutDto?.toCheckOut(document.id)

        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar Membro", e)
            return null
        }
    }

    override fun getLastCheckOuts(): Flow<List<CheckOut>> {
        return callbackFlow {
            val listener = db.collection("CheckOutCollection")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(3)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val checkOuts = snapshot?.toObjects(CheckOut::class.java).orEmpty()
                    trySend(checkOuts).isSuccess
                }
            awaitClose { listener.remove() }
        }
    }

}