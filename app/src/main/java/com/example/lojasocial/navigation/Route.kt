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
    data object Membros: Route()
    @Serializable
    data object NewMember: Route()
    @Serializable
    data class CheckOut(
        val memberId: String?
    ): Route()
}