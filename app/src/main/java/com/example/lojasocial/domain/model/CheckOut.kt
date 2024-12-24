package com.example.lojasocial.domain.model

import com.example.lojasocial.data.remote.model.CheckOutDto
import java.time.LocalDateTime
import java.util.Date

data class CheckOut (
    val id: String,
    val date: Long,
    val notes: String,
    val memberId: String,
){
    fun toCheckOutDto(): CheckOutDto{
        return CheckOutDto(date,notes,memberId)
    }
}
