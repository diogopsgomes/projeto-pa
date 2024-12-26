package com.example.lojasocial.domain.use_case

import com.example.lojasocial.data.remote.model.MemberDto
import com.example.lojasocial.data.repository.MemberRepositoryImpl
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.domain.model.User
import kotlinx.coroutines.flow.Flow

class MemberUseCase(private val repository: MemberRepositoryImpl) {

    suspend fun createMember(member: Member): Member?{
        val id = repository.createMember(member.toMemberDto())
        return id?.let {memberId ->
            return member.copy(memberId)
        }
    }

    suspend fun updateMember(member: Member): Member?{
        if(repository.updateMember(member.toMemberDto(), member.id))
            return member
        else
            return null
    }

    suspend fun getMemberByCc(cc: String): Member?{
        return repository.getMemberByCc(cc)
    }

    suspend fun getMemberById(memberId: String): Member?{
        return repository.getMemberById(memberId)
    }

    fun getMemberByIdFlow(memberId: String): Flow<Member?>{
        return repository.getMemberByIdFlow(memberId)
    }

    suspend fun updateCheckedIn(member: Member): Boolean{
        return repository.updateCheckInState(member.toMemberDto(), member.id)
    }

    fun observeMember(): Flow<List<Member>> {
        return repository.observeMembers()
    }

    fun observeNumMembersCheckedId(): Flow<Int>{
        return repository.checkUsersCheckedId()
    }

    suspend fun getNationalities(): List<String> {
        return repository.getNationalities()
    }
}