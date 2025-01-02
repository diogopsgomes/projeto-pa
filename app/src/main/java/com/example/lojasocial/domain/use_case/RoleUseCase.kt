package com.example.lojasocial.domain.use_case

import android.util.Log
import com.example.lojasocial.data.remote.model.RoleDto
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.data.repository.RoleRepositoryImpl
import com.example.lojasocial.domain.model.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class RoleUseCase(private val repository: RoleRepositoryImpl) {

    suspend fun getRoleById(roleId: String): Role? {
        return repository.getRoleById(roleId)
    }

    suspend fun getAllRoles(): List<Role> {
        return repository.getAllRoles()
    }

}