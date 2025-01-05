package com.example.lojasocial.domain.model

import com.example.lojasocial.data.remote.model.ActivityDto
import java.time.LocalDateTime
import java.util.Date

data class Activity(
    val id: String,
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
){
    fun toActivityDto(): ActivityDto{
        return ActivityDto(title, description, locality, startDate, endDate, creatorId, creatorName, creatorRole, creatorPicture, enrolled)
    }
}


