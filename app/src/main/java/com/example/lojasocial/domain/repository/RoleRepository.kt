package com.example.lojasocial.domain.repository

import com.example.lojasocial.domain.model.Role

interface RoleRepository {
    suspend fun getRoleById(roleId: String): Role?
    suspend fun getAllRoles(): List<Role>
}