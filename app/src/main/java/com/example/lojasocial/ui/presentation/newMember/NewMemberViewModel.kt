package com.example.lojasocial.ui.presentation.newMember


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.use_case.MemberUseCase
import kotlinx.coroutines.launch

class NewMemberViewModel: ViewModel() {
    //Members
    private val memberRespository = MemberRepositoryImpl()
    private val memberUseCase = MemberUseCase(memberRespository)
    //
    private val _nationalities = mutableStateOf<List<String>>(emptyList())
    val nationalities: State<List<String>> get() = _nationalities

    init {
        fetchNationalities()
    }


    suspend fun createMember(member: Member): Boolean{
        val result = memberUseCase.createMember(member)
        if(result != null)
            return true
        else
            return false
    }

    private fun fetchNationalities() {
        viewModelScope.launch {
            val fetchedNationalities = memberRespository.getNationalities()
            _nationalities.value = fetchedNationalities
        }
    }

}