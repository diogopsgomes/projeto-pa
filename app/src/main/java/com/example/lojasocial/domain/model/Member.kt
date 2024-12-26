package com.example.lojasocial.domain.model

import com.example.lojasocial.data.remote.model.MemberDto

data class Member(
    val id: String,
    val name: String,
    val surname: String,
    val cc: String,
    val phone: Int,
    val locality: String,
    val notes: String,
    val warning: Boolean,
    val checkedIn: Boolean,
    val householdId: String,
    val nationalityId: String
){
    fun toMemberDto(): MemberDto{
        return MemberDto(name,surname,cc,phone,locality,notes,warning, checkedIn ,householdId, nationalityId)
    }

    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            name,
            "${name.first()}",
            phone.toString(),
            "${phone.toString().first()}"
        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}


