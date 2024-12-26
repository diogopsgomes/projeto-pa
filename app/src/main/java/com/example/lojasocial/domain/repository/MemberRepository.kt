package com.example.lojasocial.domain.repository

import com.example.lojasocial.data.remote.model.MemberDto
import com.example.lojasocial.domain.model.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun createMember(member: MemberDto): String?
    suspend fun updateMember(member: MemberDto, memberId: String): Boolean
    fun checkUsersCheckedId(): Flow<Int>
    suspend fun updateCheckInState(member: MemberDto, memberId: String): Boolean
    suspend fun getMemberByCc(cc: String): Member?
    suspend fun getMemberById(memberId: String): Member?
    fun getMemberByIdFlow(memberId: String): Flow<Member?>
    fun observeMembers(): Flow<List<Member>>
    suspend fun getNationalities(): List<String>
}