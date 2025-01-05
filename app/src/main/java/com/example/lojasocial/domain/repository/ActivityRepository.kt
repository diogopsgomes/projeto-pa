package com.example.lojasocial.domain.repository

import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.data.remote.model.ActivityDto
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun createActivity(activity: ActivityDto): String?
    suspend fun updateActivity(activity: ActivityDto, activityId: String): Boolean
    suspend fun getActivityById(activityId: String): Activity?
    suspend fun addEnrolled(activityid: String): Boolean
    suspend fun removeEnrolled(activityid: String): Boolean
    fun observeActivities(): Flow<List<Activity>>
}