package com.example.lojasocial.data.remote.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.utils.dateToTimeStamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class CheckInDto(
    val date: Long,
    val memberId: String,
){
    fun toCheckIn(checkInId: String): CheckIn{
        return CheckIn(checkInId, date, memberId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor() :  this(dateToTimeStamp(LocalDate.now()), "")
}