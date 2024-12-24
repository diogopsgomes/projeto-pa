package com.example.lojasocial.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.lojasocial.data.remote.model.CheckInDto
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.domain.repository.CheckInRepository
import com.example.lojasocial.utils.getStartAndEndOfDay
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class CheckInRepositoryImpl: CheckInRepository {
    private val db = Firebase.firestore

    override suspend fun createCheckIn(checkIn: CheckInDto): String?{
        val utilizadoresRef = db.collection("CheckIn")
        try {
            val document = utilizadoresRef.add(checkIn).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar CheckIn", e)
            return null
        }
    }

    override suspend fun updateCheckedIn(checkIn: CheckInDto, checkInId: String): Boolean{
        val utilizadoresRef = db.collection("CheckIn")
        try {
            val document = utilizadoresRef.document(checkInId)

            document.set(checkIn).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao ao atualizar CheckIn", e)
            return false
        }
    }

    override suspend fun getCheckInById(checkInId: String): CheckIn?{
        val utilizadoresRef = db.collection("CheckIn")
        try {
            val document = utilizadoresRef.document(checkInId).get().await()

            val checkInDto = document.toObject(CheckInDto::class.java)

            return checkInDto?.toCheckIn(document.id)

        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar Membro", e)
            return null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTodayCheckIns():Int{
        val utilizadoresRef = db.collection("CheckIn")
        val (startDay, endDay) = getStartAndEndOfDay(LocalDate.now())
        try{
            val snapshot = utilizadoresRef
                .whereGreaterThanOrEqualTo("date", startDay)
                .whereGreaterThanOrEqualTo("date", endDay)
                .get()
                .await()

            return snapshot.size()
        }catch (ex: Exception){
            ex.printStackTrace()
            return 0
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCheckInsForDateFlow(): Flow<Int> = callbackFlow {
        val (startOfDay, endOfDay) = getStartAndEndOfDay(LocalDate.now())

        val listenerRegistration = db.collection("CheckIn")
            .whereGreaterThanOrEqualTo("date", startOfDay)
            .whereLessThanOrEqualTo("date", endOfDay)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // Encerra o flow em caso de erro
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    trySend(snapshot.size()) // Emite o número de documentos encontrados
                }
            }
        awaitClose {
            listenerRegistration.remove() // Remove o listener quando o flow for cancelado
        }
    }

    override fun getLastCheckIns(): Flow<List<CheckIn>> {
        return callbackFlow {
            val listener = db.collection("CheckIn")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(3)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    Log.d("Dashboard", "${snapshot?.documents?.size}")


                    val checkInsDto = snapshot?.documents?.map { document ->
                        val checkInDto = document.toObject(CheckInDto::class.java)
                        checkInDto?.toCheckIn(document.id) // Passando o id do documento
                    }
                    val checkIns = checkInsDto?.filterNotNull().orEmpty()

                    trySend(checkIns).isSuccess
                }
            awaitClose { listener.remove() }
        }
    }

}