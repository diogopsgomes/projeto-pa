package com.example.lojasocial.ui.presentation.checkOut

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.CheckOutRepositoryImpl
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.CheckOut
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.use_case.CheckOutUseCase
import com.example.lojasocial.domain.use_case.MemberUseCase
import com.example.lojasocial.utils.dateToTimeStamp
import java.time.LocalDate

class CheckOutViewModel: ViewModel() {
    //CheckOut
    private val checkOutRespository = CheckOutRepositoryImpl()
    private val checkOutUseCase = CheckOutUseCase(checkOutRespository)
    //Members
    private val memberRespository = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRespository)

    var notes = mutableStateOf("")


    suspend fun getMemberById(memberId: String?): Member?{
        if(memberId != null){
            var member = memberUseCase.getMemberById(memberId)
            if(member != null)
                return member
            return null
        }
        return null
    }




    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun CreateCheckOut(memberId: String, notes: String): Boolean{
        val checkOut = checkOutUseCase.createCheckOut(
            CheckOut("",
                dateToTimeStamp(LocalDate.now()), notes, memberId)
        )
        val member = getMemberById(memberId)
        if(member != null){
            if(checkOut != null && memberUseCase.updateCheckedIn(member))
                return true
            else
                return false
        }
        return false
    }

}