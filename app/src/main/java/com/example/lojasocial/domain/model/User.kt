package com.example.lojasocial.domain.model

import com.example.lojasocial.data.remote.model.UserDto

data class User(
    val userId: String,
    val email: String,
    val name: String,
    val surname: String,
    val picture: String,
    val roleId: String
) {
    fun toUserDto(): UserDto {
        return UserDto(
            email = email,
            name = name,
            surname = surname,
            picture = picture,
            roleId = roleId
        )
    }

    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            name,
            "${name.first()}",
        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }

}