package com.example.lojasocial.domain.use_case

import com.example.lojasocial.data.remote.model.UserActivityDto
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.data.repository.UserActivityRepositoryImpl
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.UserActivity
import kotlinx.coroutines.flow.Flow

class UserActivityUseCase(private val repository: UserActivityRepositoryImpl) {

    suspend fun addUserToActivity(useractivity: UserActivity): UserActivity?{
        val id = repository.addUserToActivity(useractivity.toUserActivityDto())
        if( id != null)
            return useractivity.copy(id)
        else
            return null
    }


    suspend fun removeUserOfActivity(useractivity: UserActivityDto): Boolean{
        return repository.removeUserOfActivity(useractivity)
    }

    fun observeActivities(): Flow<List<UserActivity>> {
        return repository.observeUserActivities()
    }


}