package com.example.lojasocial.domain.use_case

import com.example.lojasocial.data.repository.ActivityRepositoryImpl
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.model.User
import kotlinx.coroutines.flow.Flow

class ActivityUseCase(private val repository: ActivityRepositoryImpl) {

    suspend fun createActivity(activity: Activity): Activity?{
        val id = repository.createActivity(activity.toActivityDto())
        return id?.let {activityId ->
            return activity.copy(activityId)
        }
    }

    suspend fun updateActivity(activity: Activity): Activity?{
        if(repository.updateActivity(activity.toActivityDto(), activity.id))
            return activity
        else
            return null
    }

    suspend fun getActivityById(activityId: String): Activity?{
        return repository.getActivityById(activityId)
    }


    fun observeActivities(): Flow<List<Activity>> {
        return repository.observeActivities()
    }

    suspend fun addEnrolled(activityid: String): Boolean{
        return repository.addEnrolled(activityid)
    }

    suspend fun removeEnrolled(activityid: String): Boolean{
        return repository.removeEnrolled(activityid)
    }

}