package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.Member
import java.util.Date

data class MemberDto(
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
    fun toMember(memberId: String): Member {
        return Member(memberId, name, surname, cc, phone, locality, notes, warning, checkedIn, householdId, nationalityId)
    }
    constructor() :  this("","","",0,"","",false,false,"","")
}
