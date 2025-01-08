package com.example.lojasocial.ui.presentation.newActivity

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.ActivityRepositoryImpl
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.use_case.ActivityUseCase
import com.example.lojasocial.utils.getUserData

class NewActivityViewModel: ViewModel() {
    //Activities
    private val activityRespository = ActivityRepositoryImpl()
    private val activityUseCase = ActivityUseCase(activityRespository)



    //Activity data
    var title = mutableStateOf("")
    var description = mutableStateOf("")
    var local = mutableStateOf("")

    //Creator data
    var userId = mutableStateOf("")
    var name = mutableStateOf("")
    var role = mutableStateOf("")
    var picture = mutableStateOf("")
    var isAdmin = mutableStateOf(false)

    fun fetchUser(context: Context){
        val user = getUserData(context)
        if(user != null){
            userId.value = user.userId
            name.value = user.name
            role.value = user.roleId
            picture.value = user.picture
            isAdmin.value = user.roleId == "Admin"
        }
    }


    suspend fun createActivity(activity: Activity): Boolean{
        val result = activityUseCase.createActivity(activity)
        if(result != null)
            return true
        else
            return false
    }


}