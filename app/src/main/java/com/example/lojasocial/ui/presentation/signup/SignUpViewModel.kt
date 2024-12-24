package com.example.lojasocial.ui.presentation.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.domain.use_case.UserUseCase
import com.example.lojasocial.utils.AuthState
import com.example.lojasocial.utils.guardarDadosUtilizador
import com.example.lojasocial.utils.showToastMessage
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel(): ViewModel() {
    //Auth initialization
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)
    //User initialization
    private val userRepository =  UserRepositoryImpl()
    private val userUseCase = UserUseCase(userRepository)


    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    suspend fun register(email: String, password: String, name: String, surename: String, roleId: String,context: Context): User?{
        var user: User? = null
        try {
            user = authUseCase.register(email,password)
            if(user != null){
                val registerUser = user.copy(userId = "", email = user.email, name = name, surname = surename, picture = "", roleId = "")
                val saveUser = userUseCase.createUser(registerUser)
                if(saveUser != null){
                    guardarDadosUtilizador(context, saveUser)
                    _authState.value = AuthState.Authenticated
                }else{
                    //Eliminar entrada do auth -
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }catch (e: Exception){
            _authState.value = AuthState.Error(e.message!!)
        }
        if(user != null){
            showToastMessage(context, "successfully registered!")
            _authState.value = AuthState.Authenticated
            return user
        }else{
            showToastMessage(context, "invalid Email or password!")
            _authState.value = AuthState.Unauthenticated
            return null
        }
    }
}