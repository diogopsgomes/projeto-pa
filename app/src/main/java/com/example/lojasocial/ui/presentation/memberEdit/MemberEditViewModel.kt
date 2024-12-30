package com.example.lojasocial.ui.presentation.memberEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.use_case.MemberUseCase
import kotlinx.coroutines.launch

class MemberEditViewModel: ViewModel() {
    //Members
    private val memberRespository = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRespository)

    private val _nationalities = mutableStateOf<List<String>>(emptyList())
    val nationalities: State<List<String>> get() = _nationalities


    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf(0)
    var locality = mutableStateOf("")
    var nationality = mutableStateOf("")
    var cc = mutableStateOf("")
    var warning = mutableStateOf(false)
    var notes = mutableStateOf("")
    var checkedIn = mutableStateOf(false)
    var householdId = mutableStateOf("")

    init {
        fetchNationalities()
    }

    suspend fun getMemberById(memberId: String?){
        if(memberId != null){
            var member = memberUseCase.getMemberById(memberId)
            if(member != null){
                name.value = member.name
                surname.value = member.surname
                phone.value = member.phone
                locality.value = member.locality
                nationality.value = member.nationalityId
                cc.value = member.cc
                warning.value = member.warning
                notes.value = member.notes
                checkedIn.value = member.checkedIn
                householdId.value = member.householdId
            }
        }
    }

    private fun fetchNationalities() {
        viewModelScope.launch {
            val fetchedNationalities = memberRespository.getNationalities()
            _nationalities.value = fetchedNationalities
        }
    }

    suspend fun updateMember(member: Member): Boolean{
        var aux = memberUseCase.updateMember(member)
        if(aux != null)
            return true
        else
            return false
    }

}