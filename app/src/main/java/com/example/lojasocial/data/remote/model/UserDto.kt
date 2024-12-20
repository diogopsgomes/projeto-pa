package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.User

data class UserDto(
    val email: String,
    val name: String,
    val surname: String,
    val picture: String,
    val roleId: String
){
    fun toUser(userId: String): User{
        return User(userId = userId, email = email, name = name, surname = surname, picture = picture, roleId = roleId)
    }
    constructor() :  this("","","","","")
}