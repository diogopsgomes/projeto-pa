package com.example.lojasocial.data.remote.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lojasocial.domain.model.CheckOut
import com.example.lojasocial.utils.dateToTimeStamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class CheckOutDto(
    val date: Long,
    val notes: String,
    val memberId: String,
){
    fun toCheckOut(checkOutId: String): CheckOut {
        return CheckOut(checkOutId, date, notes, memberId)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() :  this(dateToTimeStamp(LocalDate.now()),"","")
}