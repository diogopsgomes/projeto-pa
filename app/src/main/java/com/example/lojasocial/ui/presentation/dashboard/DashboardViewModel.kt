package com.example.lojasocial.ui.presentation.dashboard

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.data.repository.CheckInRepositoryImpl
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.CheckIn
import com.example.lojasocial.domain.model.CheckOut
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.model.UserActivity
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.domain.use_case.CheckInUseCase
import com.example.lojasocial.domain.use_case.CheckOutUseCase
import com.example.lojasocial.domain.use_case.MemberUseCase
import com.example.lojasocial.domain.use_case.UserUseCase
import com.example.lojasocial.utils.clearUserData
import com.example.lojasocial.utils.getUserData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class DashboardViewModel(): ViewModel() {
    //Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)
    //CheckIN
    private val checkInRepository = CheckInRepositoryImpl()
    private val checkInUseCase = CheckInUseCase(checkInRepository)
    //CheckOut
//    private val checkOutRepository = CheckInRepositoryImpl
//    private val checkOutUseCase = CheckOutUseCase(checkOutRepository)
    //testes
    private val memberRep = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRep)

    private val _visits = MutableStateFlow(0)
    val visits: StateFlow<Int> get() = _visits

    private val _checkedInUsersCount = MutableStateFlow(0)
    val checkedInUsersCount: StateFlow<Int> = _checkedInUsersCount

    //Top 3 CheckIn
    private val _listaCheckIn = MutableStateFlow<List<Member>>(emptyList())
    val listaCheckIn: StateFlow<List<Member>> get() = _listaCheckIn

    //Top 3 CheckOut
    private val _listaCheckOut = MutableStateFlow<List<CheckOut>>(emptyList())
    val listaCheckOut: StateFlow<List<CheckOut>> get() = _listaCheckOut


    var name = mutableStateOf("")

    init {
        observeCheckedInUsers()
        viewModelScope.launch {
            checkInUseCase.getCheckInsForDateFlow().collect {number ->
                _visits.value = number
            }
        }
//        viewModelScope.launch {
//            getLastCheckInMembers().collect{member->
//                _listaCheckIn.value = member
//            }
//        }

    }

    fun fetchUser(context: Context){
        val user = getUserData(context)
        if(user != null)
            name.value = user.name
    }

    private fun observeCheckedInUsers(){
        viewModelScope.launch {
            memberUseCase.observeNumMembersCheckedId()
                .catch {e ->
                    Log.e("Dashboard", "${e.message}")
                }
                .collect{ numberUsers ->
                    _checkedInUsersCount.value = numberUsers
                }
        }
    }

//    private fun observeLastCheckedIn(){
//        viewModelScope.launch {
//            checkInRepository.getLastCheckIns()
//                .catch {e->
//                    Log.e("Dashboard", "${e.message}")
//                }
//                .collect{ checkins ->
//                    _listaCheckIn.value = checkins
//                }
//        }
//    }

//    private fun observeLastCheckedOut(){
//        viewModelScope.launch {
//            checkOutUseCase.getLastCheckOuts()
//                .catch {e->
//                    Log.e("Dashboard", "${e.message}")
//                }
//                .collect{ checkout ->
//                    _listaCheckOut.value = checkout
//                }
//        }
//    }

    //Utlimo
//    private fun getLastCheckInMembers(): Flow<List<Member>> = flow {
//        checkInUseCase.getLastCheckIns().collect { checkIns ->
//            val memberList = checkIns.mapNotNull { checkIn ->
//                Log.d("Dashboard", "Teste -> ${checkIn}")
//                memberUseCase.getMemberByIdFlow(checkIn.memberId).firstOrNull() // Obtém o membro correspondente
//                Log.d("Dashboard", "Teste -> depois ${checkIn}")
//            }
//            emit(memberList) // Emite a lista de membros
//        }
//    }

//    private fun getLastCheckInMembers(): Flow<List<Member>> = flow {
//        checkInUseCase.getLastCheckIns().collect { checkIns ->
//            val memberList = checkIns.mapNotNull { checkIn ->
//                Log.d("Dashboard", "Teste -> $checkIn")
//
//                // Coletando o Flow do membro e obtendo o valor do membro
//                val member = memberUseCase.getMemberByIdFlow(checkIn.memberId).firstOrNull()
//
//                Log.d("Dashboard", "Teste -> depois $checkIn")
//                member // Retorna o membro se ele não for nulo, caso contrário, será ignorado
//            }
//
//            emit(memberList) // Emite a lista de membros
//        }
//    }




    suspend fun logout(context: Context){
        try {
            clearUserData(context)
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun getVisitsNumber(){
//        visits.value = checkInUseCase.getTodayCheckIns()
//    }


    //Testes
    suspend fun createMember(member: Member){
        var res = memberUseCase.createMember(member)
    }
    suspend fun updateMember(member: Member){
        var res = memberUseCase.updateMember(member)
    }
}