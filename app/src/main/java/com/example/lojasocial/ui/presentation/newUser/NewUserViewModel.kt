package com.example.lojasocial.ui.presentation.newUser

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.data.repository.RoleRepositoryImpl
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.model.Role
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.domain.use_case.RoleUseCase
import com.example.lojasocial.domain.use_case.UserUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class NewUserViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)
    //Members
    private val userRespository = UserRepositoryImpl()
    private val userUseCase = UserUseCase(userRespository)

    private val roleRepository = RoleRepositoryImpl()
    private val roleUseCase = RoleUseCase(roleRepository)

    //Roles
    private val _roles = mutableStateOf<List<Role>>(emptyList())
    val roles: State<List<Role>> = _roles

    init {
        fetchRoles()
    }

    suspend fun createUser(user: User, password: String): Boolean{
        val userResult = authUseCase.register(user.email, password)
        if(userResult != null){
            val result = userUseCase.createUser(user)
            if(result != null)
                return true
            else
                return false
        }else
            return false
    }

    private fun fetchRoles() {
        viewModelScope.launch {
            val fetchedRoles = roleRepository.getAllRoles()
            _roles.value = fetchedRoles
        }
    }



}