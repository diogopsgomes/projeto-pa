package com.example.lojasocial.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Route{
    @Serializable
    data object Loading: Route()
    @Serializable
    data object Dashboard: Route()
    //    data class Home(
//        var email: String?
//    ): Route()
    @Serializable
    data object Login: Route()
    @Serializable
    data object Signup: Route()
    @Serializable
    data object teste: Route()
    @Serializable
    data object Membros: Route()
    @Serializable
    data object Profile: Route()
    @Serializable
    data object Atividades: Route()
    @Serializable
    data object NewMember: Route()
    @Serializable
    data object NewUser: Route()
    @Serializable
    data object Users: Route()
    @Serializable
    data object NewActivity: Route()
    @Serializable
    data object Activity: Route()
    @Serializable
    data class MemberEdit(
        val memberId: String?
    ): Route()
    @Serializable
    data class CheckOut(
        val memberId: String?
    ): Route()
}