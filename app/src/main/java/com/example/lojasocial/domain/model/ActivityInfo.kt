package com.example.lojasocial.domain.model

import java.time.LocalDateTime

data class ActivityInfo (
    val id: String,
    val title: String,
    val description: String,
    val locality: String,
    val startDate: Long,
    val endDate: Long,
    val creatorId: String,
    val creatorName: String,
    val creatorRole: String,
    val creatorPicture: String,
    val enrolled: Int,
    val joined: Boolean
){
    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            creatorName,
            "${creatorName.first()}",
            locality,
            "${locality.first()}",
            creatorRole,
            "${creatorRole.first()}",
            description,
            "${description.first()}",
            title,
            "${title.first()}",
        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}