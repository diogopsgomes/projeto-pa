package com.example.lojasocial.data.remote.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.utils.dateToTimeStamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class ActivityDto(
    val title: String,
    val description: String,
    val locality: String,
    val startDate: Long,
    val endDate: Long,
    val creatorId: String,
    val creatorName: String,
    val creatorRole: String,
    val creatorPicture: String,
    val enrolled: Int
) {
    fun toActivity(activityId: String): Activity{
        return Activity(activityId,title, description, locality, startDate, endDate,creatorId, creatorName, creatorRole, creatorPicture, enrolled)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    constructor() :  this("","","", dateToTimeStamp(LocalDate.now()),dateToTimeStamp(LocalDate.now()),"","","","",0)
}