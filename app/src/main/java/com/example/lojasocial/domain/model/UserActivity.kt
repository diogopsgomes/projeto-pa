package com.example.lojasocial.domain.model

import com.example.lojasocial.data.remote.model.UserActivityDto

data class UserActivity (
    val id: String,
    val userId: String,
    val activityId: String
){
    fun toUserActivityDto(): UserActivityDto{
        return UserActivityDto(userId,activityId)
    }
}
