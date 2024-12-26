package com.example.lojasocial.ui.presentation.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.navigation.Route

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Dashboard(navController: NavHostController, dashboardViewModel: DashboardViewModel) {
    val checkedInUsers = dashboardViewModel.checkedInUsersCount.collectAsState()
    val context = LocalContext.current
    val checkInsToday by dashboardViewModel.visits.collectAsState()


    LaunchedEffect(Unit) {
        dashboardViewModel.fetchUser(context)
    }

    Scaffold(
        containerColor = Color(0xFFFFFFFF), // Define a cor de fundo diretamente no Scaffold
        bottomBar = {
            BottomBar(navController = navController, Route.Dashboard)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color(0xFFFFFFFF))
        ) {
            Text(
                text = "Olá, ${dashboardViewModel.name.value}!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .weight(1f)
                        .background(Color(0xFFF4F0EF))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "VISITAS HOJE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${checkInsToday}",
                            fontSize = 64.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .weight(1f)
                        .background(Color(0xFFF4F0EF))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "EM LOJA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = checkedInUsers.value.toString(),
                            fontSize = 64.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = "Últimas Entradas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFF4F0EF))
                        .padding(start = 26.dp, end = 26.dp)
                ){
                        Column(modifier = Modifier.padding(vertical = 8.dp)){
                            Text(text = "Francisco Silva",
                                fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "João Santos",
                                fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Leonor Ferreira",
                                fontSize = 16.sp)
                        }
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = "Últimas Saídas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFF4F0EF))
                        .padding(start = 26.dp, end = 26.dp)
                ){
                    Column(modifier = Modifier.padding(vertical = 8.dp)){
                        Text(text = "Francisco Silva",
                            fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "João Santos",
                            fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Leonor Ferreira",
                            fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = "Top Nacionalidade Hoje",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFF4F0EF))
                        .padding(start = 26.dp, end = 26.dp)
                        .padding(vertical = 8.dp)
                ){
                    Text(text = "Portuguesa",
                        fontSize = 16.sp)
                }
            }
        }
    }
}