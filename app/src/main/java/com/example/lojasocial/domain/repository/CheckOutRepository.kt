package com.example.lojasocial.domain.repository

import com.example.lojasocial.data.remote.model.CheckOutDto
import com.example.lojasocial.domain.model.CheckOut
import kotlinx.coroutines.flow.Flow

interface CheckOutRepository {
    suspend fun createCheckOut(checkOut: CheckOutDto): String?
    suspend fun updateCheckOut(checkOut: CheckOutDto, checkOutId: String): Boolean
    suspend fun getCheckOutById(checkOutId: String): CheckOut?
    fun getLastCheckOuts(): Flow<List<CheckOut>>
}