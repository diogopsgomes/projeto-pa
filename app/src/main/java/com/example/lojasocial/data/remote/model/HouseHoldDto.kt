package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.HouseHold
import java.util.Date

data class HouseHoldDto (
    val description: String
){
    fun toHouseHold(houseHoldId: String): HouseHold{
        return HouseHold(houseHoldId, description)
    }
    constructor() :  this("")
}