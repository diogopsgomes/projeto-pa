package com.example.lojasocial.ui.presentation.teste

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.ActivityRepositoryImpl
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.use_case.ActivityUseCase
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.domain.use_case.MemberUseCase
import com.example.lojasocial.utils.clearUserData
import com.google.firebase.auth.FirebaseAuth

class TesteViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)

    //testes
    private val memberRep = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRep)

    private val activityRep = ActivityRepositoryImpl()
    private val activityUseCase = ActivityUseCase(activityRep)


    suspend fun logout(context: Context){
        try {
            clearUserData(context)
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }


    //Testes
    suspend fun createMember(member: Member){
        var res = memberUseCase.createMember(member)
    }
    suspend fun updateMember(member: Member){
        var res = memberUseCase.updateMember(member)
    }

    //
    suspend fun createActivity(activity: Activity){
        var res = activityUseCase.createActivity(activity)
    }
    suspend fun updateActivity(activity: Activity){
        var res = activityUseCase.updateActivity(activity)
    }
}