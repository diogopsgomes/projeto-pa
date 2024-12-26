package com.example.lojasocial.ui.presentation.member

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.CheckInRepositoryImpl
import com.example.lojasocial.data.repository.CheckOutRepositoryImpl
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.domain.model.CheckOut
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.use_case.CheckInUseCase
import com.example.lojasocial.domain.use_case.CheckOutUseCase
import com.example.lojasocial.domain.use_case.MemberUseCase
import com.example.lojasocial.utils.dateToTimeStamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class MemberViewModel: ViewModel() {
    //Members
    private val memberRespository = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRespository)
    //CheckIn
    private val checkInRespository = CheckInRepositoryImpl()
    private val checkInUseCase = CheckInUseCase(checkInRespository)
    //CheckOut
    private val checkOutRespository = CheckOutRepositoryImpl()
    private val checkOutUseCase = CheckOutUseCase(checkOutRespository)


    //Flow Members
    private val _listaMembros = MutableStateFlow<List<Member>>(emptyList())
    //val listaMembros: StateFlow<List<Member>> get() = _listaMembros

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val listaMembros = searchText
        .combine(_listaMembros){ text, members ->
            if(text.isBlank()){
                members
            } else{
                members.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _listaMembros.value
        )



    init {
        fetchMembers()
    }


    private fun fetchMembers(){
        viewModelScope.launch {
            memberUseCase.observeMember()
                .catch {e ->
                    Log.e("Members", "${e.message}")
                }
                .collect { listaAtualizada ->
                    _listaMembros.value = listaAtualizada
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun CreateCheckIn(member: Member): Boolean{
        val checkIn = checkInUseCase.createCheckIn(CheckIn("", dateToTimeStamp(LocalDate.now()), member.id))
        if(checkIn != null && memberUseCase.updateCheckedIn(member))
            return true
        else
            return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun CreateCheckOut(member: Member, notes: String): Boolean{
        val checkOut = checkOutUseCase.createCheckOut(CheckOut("",dateToTimeStamp(LocalDate.now()), notes, member.id))
        if(checkOut != null && memberUseCase.updateCheckedIn(member))
            return true
        else
            return false
    }

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }

}