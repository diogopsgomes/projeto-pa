package com.example.lojasocial.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lojasocial.ui.presentation.activity.ActivityScreen
import com.example.lojasocial.ui.presentation.activity.ActivityViewModel
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
import com.example.lojasocial.ui.presentation.memberEdit.MemberEditScreen
import com.example.lojasocial.ui.presentation.memberEdit.MemberEditViewModel
import com.example.lojasocial.ui.presentation.newActivity.NewActivityScreen
import com.example.lojasocial.ui.presentation.newActivity.NewActivityViewModel
import com.example.lojasocial.ui.presentation.newMember.NewMemberScreen
import com.example.lojasocial.ui.presentation.newMember.NewMemberViewModel
import com.example.lojasocial.ui.presentation.newUser.NewUserScreen
import com.example.lojasocial.ui.presentation.newUser.NewUserViewModel
import com.example.lojasocial.ui.presentation.profile.ProfileScreen
import com.example.lojasocial.ui.presentation.profile.ProfileViewModel
import com.example.lojasocial.ui.presentation.signup.SignUp
import com.example.lojasocial.ui.presentation.signup.SignUpViewModel
import com.example.lojasocial.ui.presentation.teste.TesteViewModel
import com.example.lojasocial.ui.presentation.teste.testeScreen
import com.example.lojasocial.ui.presentation.users.UsersScreen
import com.example.lojasocial.ui.presentation.users.UsersViewModel

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
        composable<Route.teste>{
            val testeViewModel: TesteViewModel = viewModel()
            testeScreen(navController = navController, testeViewModel)
        }
        composable<Route.Membros>{
            val memberViewModel: MemberViewModel = viewModel()
            MemberScreen(navController = navController, memberViewModel)
        }
        composable<Route.NewMember>{
            val newMemberViewModel: NewMemberViewModel = viewModel()
            NewMemberScreen(navController = navController, newMemberViewModel)
        }
        composable<Route.NewUser>{
            val newUserViewModel: NewUserViewModel = viewModel()
            NewUserScreen(navController = navController, newUserViewModel)
        }
        composable<Route.Users>{
            val usersViewModel: UsersViewModel = viewModel()
            UsersScreen(navController = navController, usersViewModel)
        }
        composable<Route.NewActivity>{
            val newActivityViewModel: NewActivityViewModel = viewModel()
            NewActivityScreen(navController = navController, newActivityViewModel)
        }
        composable<Route.Activity>{
            val activityViewModel: ActivityViewModel = viewModel()
            ActivityScreen(navController = navController, activityViewModel)
        }
        composable<Route.Profile>{
            val profileViewModel: ProfileViewModel = viewModel()
            Log.d("teste", "Passou no navGraph")
            ProfileScreen(navController = navController, profileViewModel)
        }
        composable<Route.MemberEdit>{backStackEntry ->
            val member = backStackEntry.toRoute<Route.MemberEdit>()
            val memberEditViewModel: MemberEditViewModel = viewModel()
            MemberEditScreen(navController = navController, memberEditViewModel, member.memberId)
        }
        composable<Route.CheckOut>{backStackEntry ->
            val member = backStackEntry.toRoute<Route.CheckOut>()
            val checkOutViewModel: CheckOutViewModel = viewModel()
            CheckOutScreen(navController = navController, checkOutViewModel, member.memberId)
        }
    }
}