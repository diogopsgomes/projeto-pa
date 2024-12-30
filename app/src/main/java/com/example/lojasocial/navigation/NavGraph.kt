package com.example.lojasocial.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lojasocial.ui.presentation.checkOut.CheckOutScreen
import com.example.lojasocial.ui.presentation.checkOut.CheckOutViewModel
import com.example.lojasocial.ui.presentation.dashboard.Dashboard
import com.example.lojasocial.ui.presentation.dashboard.DashboardViewModel
import com.example.lojasocial.ui.presentation.loading.LoadingApp
import com.example.lojasocial.ui.presentation.loading.LoadingViewModel
import com.example.lojasocial.ui.presentation.login.Login
import com.example.lojasocial.ui.presentation.login.LoginViewModel
import com.example.lojasocial.ui.presentation.member.MemberScreen
import com.example.lojasocial.ui.presentation.member.MemberViewModel
import com.example.lojasocial.ui.presentation.newMember.NewMemberScreen
import com.example.lojasocial.ui.presentation.newMember.NewMemberViewModel
import com.example.lojasocial.ui.presentation.signup.SignUp
import com.example.lojasocial.ui.presentation.signup.SignUpViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(loadingViewModel: LoadingViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Loading
    ){
        composable<Route.Loading>{
            LoadingApp(navController = navController, loadingViewModel)
        }
        composable<Route.Login>{
            val loginViewModel: LoginViewModel = viewModel()
            Login(navController = navController, loginViewModel)
        }
        composable<Route.Signup>{
            val singUpViewModel: SignUpViewModel = viewModel()
            SignUp(navController = navController, singUpViewModel)
        }
        composable<Route.Dashboard>{
            val dashboardViewModel: DashboardViewModel = viewModel()
            Dashboard(navController = navController,dashboardViewModel)
        }
        composable<Route.Membros>{
            val memberViewModel: MemberViewModel = viewModel()
            MemberScreen(navController = navController, memberViewModel)
        }
        composable<Route.NewMember>{
            val newMemberViewModel: NewMemberViewModel = viewModel()
            NewMemberScreen(navController = navController, newMemberViewModel)
        }
        composable<Route.CheckOut>{backStackEntry ->
            val member = backStackEntry.toRoute<Route.CheckOut>()
            val checkOutViewModel: CheckOutViewModel = viewModel()
            CheckOutScreen(navController = navController, checkOutViewModel, member.memberId)
        }
    }
}