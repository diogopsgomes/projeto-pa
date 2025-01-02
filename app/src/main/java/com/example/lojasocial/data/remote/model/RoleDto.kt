package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.Role


data class RoleDto(
    var description: String

){
    fun toRole(roleId: String): Role {
        return Role(roleId = roleId, description = description)
    }
    constructor() :  this("")
}
