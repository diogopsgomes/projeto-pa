package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.UserActivity

class UserActivityDto(
    val userId: String,
    val activityId: String
) {
    fun toUserActivity(userActivityId: String): UserActivity{
        return UserActivity(userActivityId, userId, activityId)
    }
    constructor() :  this("", "")
}