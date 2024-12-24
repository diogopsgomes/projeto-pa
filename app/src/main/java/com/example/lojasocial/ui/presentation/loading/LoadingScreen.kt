package com.example.lojasocial.ui.presentation.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lojasocial.navigation.Route

@Composable
fun LoadingApp(navController: NavHostController, loadingViewModel: LoadingViewModel){
    val isLoggedIn by loadingViewModel.isLoggedIn.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(isLoggedIn) {
        loadingViewModel.isLoggedIn(context)
        isLoggedIn?.let {
            if (it) {
                navController.navigate(Route.Dashboard)
            } else {
                navController.navigate(Route.Login)
            }
        }
    }

    Scaffold { innerpadding ->
        Column (modifier = Modifier
            .padding(innerpadding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator(
                color = Color.Gray,
                strokeWidth = 4.dp,
            )
        }
    }
}