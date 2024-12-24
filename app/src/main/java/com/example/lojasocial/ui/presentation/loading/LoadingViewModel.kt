package com.example.lojasocial.ui.presentation.loading

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.utils.getUserData
import com.google.firebase.auth.FirebaseAuth

class LoadingViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)

    private val _isLoggedIn = MutableLiveData<Boolean?>()
    val isLoggedIn: LiveData<Boolean?> = _isLoggedIn

    init {
        _isLoggedIn.value = authUseCase.isLoggedIn()
    }

    fun isLoggedIn(context: Context){
        val user = getUserData(context)
        if(user != null)
            _isLoggedIn.value = true
        else
            _isLoggedIn.value = false
    }

}