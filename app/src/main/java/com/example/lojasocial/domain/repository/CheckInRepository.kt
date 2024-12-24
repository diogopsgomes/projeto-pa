package com.example.lojasocial.domain.repository

import com.example.lojasocial.data.remote.model.CheckInDto
import com.example.lojasocial.domain.model.CheckIn

import kotlinx.coroutines.flow.Flow

interface CheckInRepository {
    suspend fun createCheckIn(checkIn: CheckInDto): String?
    suspend fun updateCheckedIn(checkIn: CheckInDto, checkInId: String): Boolean
    suspend fun getCheckInById(checkInId: String): CheckIn?
    suspend fun getTodayCheckIns():Int
    fun getCheckInsForDateFlow(): Flow<Int>
    fun getLastCheckIns(): Flow<List<CheckIn>>
}