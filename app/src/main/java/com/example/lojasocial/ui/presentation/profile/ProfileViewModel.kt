package com.example.lojasocial.ui.presentation.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.domain.use_case.UserUseCase
import com.example.lojasocial.utils.clearUserData
import com.example.lojasocial.utils.getUserData
import com.example.lojasocial.utils.guardarDadosUtilizador
import com.example.lojasocial.utils.showToastError
import com.example.lojasocial.utils.showToastMessage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.math.log

class ProfileViewModel: ViewModel() {
    //Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)
    //User
    private val userRepository = UserRepositoryImpl()
    private val userUseCase = UserUseCase(userRepository)


    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?> = _userData


    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var role = mutableStateOf("")
    var userId = mutableStateOf("")
    var picture = mutableStateOf("")
    var isAdmin = mutableStateOf(false)



    suspend fun fetchUser(context: Context){
        val user = getUserData(context)
        if(user != null){
            Log.d("teste", "TEstes : ${user}")
            name.value = user.name
            surname.value = user.surname
            email.value = user.email
            role.value = user.roleId
            userId.value = user.userId
            picture.value = user.picture
            isAdmin.value = user.roleId == "Admin"
        }else
            logout(context)
    }



    suspend fun logout(context: Context){
        try {
            clearUserData(context)
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }

    suspend fun updateUser(context: Context){
        var user = userUseCase.updateUser(User(userId.value, email.value, name.value, surname.value, picture.value, role.value))
        if(user != null){
            showToastMessage(context, "Dados Atualizados com sucesso!")
            guardarDadosUtilizador(context, user)
        }
        else
            showToastMessage(context, "Erro ao atualizar dados!")
    }

    fun uploadAndSaveImage(userId: String, imageUri: Uri) {
        viewModelScope.launch {
            userUseCase.uploadUserImage(userId, imageUri)
        }
    }
}