package com.example.lojasocial.domain.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lojasocial.data.repository.CheckInRepositoryImpl
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.domain.model.CheckOut
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CheckInUseCase(private val repository: CheckInRepositoryImpl) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createCheckIn(checkIn: CheckIn): CheckIn?{
        val id = repository.createCheckIn(checkIn.toCheckInDto())
        return id?.let { checkInId ->
            return checkIn.copy(checkInId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateCheckIn(checkIn: CheckIn): CheckIn?{
        if(repository.updateCheckedIn(checkIn.toCheckInDto(), checkIn.id))
            return checkIn
        else
            return null
    }

    suspend fun getCheckInById(checkInId: String): CheckIn?{
        return repository.getCheckInById(checkInId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTodayCheckIns(): Int{
        return repository.getTodayCheckIns()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCheckInsForDateFlow(): Flow<Int> {
        return repository.getCheckInsForDateFlow()
    }

    fun getLastCheckIns(): Flow<List<CheckIn>>{
        return repository.getLastCheckIns()
    }

}