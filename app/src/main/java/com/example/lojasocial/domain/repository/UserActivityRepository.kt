package com.example.lojasocial.domain.repository

import com.example.lojasocial.data.remote.model.UserActivityDto
import com.example.lojasocial.domain.model.UserActivity
import kotlinx.coroutines.flow.Flow

interface UserActivityRepository {
    suspend fun addUserToActivity(userActivity: UserActivityDto): String?
    suspend fun removeUserOfActivity(userActivity: UserActivityDto): Boolean
    fun observeUserActivities(): Flow<List<UserActivity>>
}