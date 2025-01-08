package com.example.lojasocial.ui.presentation.activity

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.remote.model.ActivityDto
import com.example.lojasocial.data.remote.model.UserActivityDto
import com.example.lojasocial.data.repository.ActivityRepositoryImpl
import com.example.lojasocial.data.repository.UserActivityRepositoryImpl
import com.example.lojasocial.data.repository.UserRepositoryImpl
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.ActivityInfo
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.model.UserActivity
import com.example.lojasocial.domain.use_case.ActivityUseCase
import com.example.lojasocial.domain.use_case.UserActivityUseCase
import com.example.lojasocial.domain.use_case.UserUseCase
import com.example.lojasocial.utils.getUserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ActivityViewModel: ViewModel() {
    //Activities
    private val activityRespository = ActivityRepositoryImpl()
    private val activityUseCase = ActivityUseCase(activityRespository)
    //users
    private val userRespository = UserRepositoryImpl()
    private val userUseCase = UserUseCase(userRespository)
    //UserActivity
    private val userActivityRespository = UserActivityRepositoryImpl()
    private val userActivityUseCase = UserActivityUseCase(userActivityRespository)

    private val _activitiesData = MutableStateFlow<List<Activity>>(emptyList())
    val activitiesData: StateFlow<List<Activity>> get() = _activitiesData

    private val _userActivitiesData = MutableStateFlow<List<UserActivity>>(emptyList())
    val userActivitiesData: StateFlow<List<UserActivity>> get() = _userActivitiesData

    private val _userActivitiesInfo = MutableStateFlow<List<ActivityInfo>>(emptyList())
    //val userActivitiesInfo: StateFlow<List<ActivityInfo>> get() = _userActivitiesInfo

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val userActivitiesInfo = searchText.combine(_userActivitiesInfo){
            text, activity ->
        if(text.isBlank()){
            activity
        } else{
            activity.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _userActivitiesInfo.value
        )

    val userId = mutableStateOf("")

    init {
        fetchActivities()
        fetchUserActivities()
    }


    fun loadUserData(user: User) {
        _userData.value = user
    }

    fun fetchUser(context: Context){
        val user = getUserData(context)
        if(user != null){
            userId.value = user.userId
            fetchActivitiesInfo()
        }
    }


    fun fetchUserActivities(){
        viewModelScope.launch {
            userActivityRespository.observeUserActivities()
                .catch {e ->
                    Log.e("UserActivities", "${e.message}")
                }
                .collect { updatedList ->
                    _userActivitiesData.value = updatedList
                }
        }
    }

    fun fetchActivities(){
        viewModelScope.launch {
            activityRespository.observeActivities()
                .catch {e ->
                    Log.e("Activities", "${e.message}")
                }
                .collect { updatedList ->
                    _activitiesData.value = updatedList
                }
        }
    }

    fun fetchActivitiesInfo(){
        viewModelScope.launch {
            getUserActivitiesDetails(userId.value)
                .catch { e->
                    Log.e("Erro", "${e.message}")
                }
                .collect{ activitiesInfo ->
                    _userActivitiesInfo.value = activitiesInfo
                }
        }
    }

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }


    fun getUserActivitiesDetails(userId: String): Flow<List<ActivityInfo>> {
        return combine(activitiesData, userActivitiesData) { activities, userActivities ->
            activities.map { activity ->
                // Filtra as inscrições de usuários para pegar as que pertencem à atividade atual
                val usersInActivity = userActivities.filter { it.activityId == activity.id }

                // Verifica se o usuário está inscrito nesta atividade, usando a tabela de ligação
                val enrolled = usersInActivity.any { it.userId == userId }

                // Criação do modelo de informação que será exibido na UI
                ActivityInfo(
                    id = activity.id,
                    title = activity.title,
                    description = activity.description,
                    locality = activity.locality,
                    startDate = activity.startDate,
                    endDate = activity.endDate,
                    creatorId = activity.creatorId,
                    creatorName = activity.creatorName,
                    creatorRole = activity.creatorRole,
                    creatorPicture = activity.creatorPicture,
                    enrolled = activity.enrolled,
                    joined = enrolled // Indica se o usuário está inscrito nesta atividade
                )
            }
        }
    }




    suspend fun joinActivity(activityId: String): Boolean{
        val userActivity = userActivityUseCase.addUserToActivity(UserActivity("",userId.value, activityId))
        if(userActivity != null && activityUseCase.addEnrolled(activityId))
            return true
        else
            return false
    }

    suspend fun leaveActivity(activity: Activity): Boolean{
        Log.d("Activity", "${activity.id} / ")
        if(userActivityUseCase.removeUserOfActivity(UserActivityDto(activityId = activity.id, userId = userId.value)) && activityUseCase.removeEnrolled(activity.id))
            return true
        else
            return false
    }




}