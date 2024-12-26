package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.MemberDto
import com.example.lojasocial.data.remote.model.UserDto
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.repository.MemberRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class MemberRepositoryImpl: MemberRepository {
    private val db = Firebase.firestore

    override suspend fun createMember(member: MemberDto): String?{
        val utilizadoresRef = db.collection("Member")
        try {
            val document = utilizadoresRef.add(member).await()

            return document.id
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar membro", e)
            return null
        }
    }

    override suspend fun updateMember(member: MemberDto, memberId: String): Boolean{
        val utilizadoresRef = db.collection("Member")
        try {
            val document = utilizadoresRef.document(memberId)

            document.set(member).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao ao atualizar membro", e)
            return false
        }
    }

    override fun checkUsersCheckedId(): Flow<Int> = callbackFlow{
        val utilizadoresRef = db.collection("Member")
        val document = utilizadoresRef
            .whereEqualTo("checkedIn", true)
            .addSnapshotListener{ querySnapshot, error ->
                if(error != null){
                    close(error)
                    return@addSnapshotListener
                }

                trySend(querySnapshot?.size() ?: 0).isSuccess
            }
        awaitClose { document.remove() }
    }


    override suspend fun updateCheckInState(member: MemberDto, memberId: String): Boolean{
        try{
            val documentId = db.collection("Member")
                .document(memberId)
            documentId.update("checkedIn", !member.checkedIn).await()
            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    override suspend fun getMemberByCc(cc: String): Member?{
        val utilizadoresRef = db.collection("Member")
        try {
            val document = utilizadoresRef.whereEqualTo("cc", cc).get().await()
            val memberDto = document.firstOrNull()?.toObject(MemberDto::class.java)
            return memberDto?.toMember(document.documents.firstOrNull()!!.id)
        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar Membro", e)
            return null
        }
    }


    override suspend fun getMemberById(memberId: String): Member?{
        val utilizadoresRef = db.collection("Member")
        try {
            val document = utilizadoresRef.document(memberId).get().await()

            val memberDto = document.toObject(MemberDto::class.java)
            return memberDto?.toMember(document.id)
        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar Membro", e)
            return null
        }
    }

    override fun getMemberByIdFlow(memberId: String): Flow<Member?> = flow {
        val utilizadoresRef = db.collection("Member")
        try {
            val document = utilizadoresRef.document(memberId).get().await()
            val memberDto = document.toObject(MemberDto::class.java)
            emit(memberDto?.toMember(document.id))
        } catch (e: Exception) {
            Log.e("Firestore", "Erro procurar Membro", e)
            emit(null) // Emite um valor nulo em caso de erro
        }
    }


    //Retorna Flow<Member>
    override fun observeMembers() =
        db.collection("Member")
            .snapshots().map { snapshot ->
                snapshot.map {document ->
                    document.toObject(MemberDto::class.java).toMember(document.id)
                }
            }.filterNotNull()


    override suspend fun getNationalities(): List<String> {
        val nationalitiesRef = db.collection("Nationality")
        return try {
            val documents = nationalitiesRef.get().await()
            documents.documents.mapNotNull { document ->
                document.getString("name")
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar nacionalidades", e)
            emptyList()
        }
    }

}


