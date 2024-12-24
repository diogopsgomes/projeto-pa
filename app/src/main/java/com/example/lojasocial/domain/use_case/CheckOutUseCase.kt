package com.example.lojasocial.domain.use_case

import com.example.lojasocial.data.repository.CheckOutRepositoryImpl
import com.example.lojasocial.domain.model.CheckOut
import kotlinx.coroutines.flow.Flow

class CheckOutUseCase(private val repository: CheckOutRepositoryImpl) {

    suspend fun createCheckOut(checkOut: CheckOut): CheckOut?{
        val id = repository.createCheckOut(checkOut.toCheckOutDto())
        return id?.let {checkOutId ->
            return checkOut.copy(checkOutId)
        }
    }
    suspend fun updateCheckOut(checkOut: CheckOut): CheckOut?{
        if(repository.updateCheckOut(checkOut.toCheckOutDto(), checkOut.id))
            return checkOut
        else
            return null
    }

    suspend fun getCheckOutById(checkOutId: String): CheckOut?{
        return repository.getCheckOutById(checkOutId)
    }

    fun getLastCheckOuts(): Flow<List<CheckOut>> {
        return repository.getLastCheckOuts()
    }

}