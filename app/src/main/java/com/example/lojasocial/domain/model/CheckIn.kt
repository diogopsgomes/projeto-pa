package com.example.lojasocial.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lojasocial.data.remote.model.CheckInDto
import com.example.lojasocial.data.remote.model.CheckOutDto
import com.example.lojasocial.utils.dateToTimeStamp
import com.google.type.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class CheckIn(
    val id: String,
    val date: Long,
    val memberId: String,
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun toCheckInDto(): CheckInDto {
        return CheckInDto(dateToTimeStamp(LocalDate.now()), memberId)
    }
}

