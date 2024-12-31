package com.example.lojasocial.ui.presentation.users

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.UserUseCase
import com.example.lojasocial.utils.getUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsersViewModel: ViewModel() {
    //users
    private val userRespository = UserRepositoryImpl()
    private val userUseCase = UserUseCase(userRespository)


    //Flow users
    private val _listaUsers = MutableStateFlow<List<User>>(emptyList())
    //val listaUsers: StateFlow<List<User>> get() = _listaUsers

    val isAdmin = mutableStateOf(false)

    init {
        fetchUsers()
    }

    fun fetchUser(context: Context){
        val user = getUserData(context)
        if(user != null){
            isAdmin.value = user.roleId == "Admin"
        }
    }



    fun fetchUsers(){
        viewModelScope.launch {
            userUseCase.observeUsers()
                .catch {e ->
                    Log.e("User", "${e.message}")
                }
                .collect { listaAtualizada ->
                    _listaUsers.value = listaAtualizada
                }
        }
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val listaUsers = searchText.combine(_listaUsers){
            text, users ->
        if(text.isBlank()){
            users
        } else{
            users.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _listaUsers.value
    )

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }


}