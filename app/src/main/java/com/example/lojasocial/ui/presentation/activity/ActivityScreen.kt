package com.example.lojasocial.ui.presentation.activity

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.domain.model.ActivityInfo
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.formatDate
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityScreen(navController: NavHostController, activityViewModel: ActivityViewModel) {
    val activityList = activityViewModel.activitiesData.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        activityViewModel.fetchUser(context)
    }

    //val testeList = activityViewModel.getUserActivitiesDetails(activityViewModel.userId.value).collectAsState(initial = emptyList())
    val searchText by activityViewModel.searchText.collectAsState()
    val testeList by activityViewModel.userActivitiesInfo.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        bottomBar = {
            BottomBar(navController = navController, Route.Atividades)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color(0xFFFFFFFF))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Atividades",
                    fontSize = 22.sp
                )
                SmallFloatingActionButton(
                    onClick = {
                        navController.navigate(Route.NewActivity)
                    },
                    modifier = Modifier.shadow(
                        elevation = 15.dp,
                        shape = FloatingActionButtonDefaults.smallShape
                    ),
                    containerColor = Color(0xFFF4F0EF),
                    contentColor = Color(0xFF49454F),
                )
                {
                    Icon(Icons.Filled.Add, "Criar Membro")
                }

            }
            Spacer(modifier = Modifier.height(25.dp))


            OutlinedTextField(
                value = searchText,
                onValueChange = activityViewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text("Pesquisa") },
                placeholder = { Text("Procurar membro...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ícone de pesquisa"
                    )
                },
                shape = RoundedCornerShape(16.dp), // Define cantos curvos
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Remove a borda inferior
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = Color(0xFFF5F5F5) // Cor de fundo
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(testeList) { activity ->
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountCircle,
                                            contentDescription = "Avatar",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = activity.creatorName,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = activity.creatorRole,
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = activity.title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )

                                    Text(
                                        text = activity.locality,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = activity.description,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = formatDate(activity.startDate),
                                            fontSize = 14.sp,
                                            color = Color.Black
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${activity.enrolled} aderiram",
                                                fontSize = 14.sp,
                                                color = Color.Black
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            if(activity.joined){
                                                Button(
                                                    onClick = {
                                                        coroutineScope.launch {
                                                            if(
                                                            activityViewModel.leaveActivity(
                                                                Activity(
                                                                activity.id,
                                                                    activity.title,
                                                                    activity.description,
                                                                    activity.locality,
                                                                    activity.startDate,
                                                                    activity.endDate,
                                                                    activity.creatorId,
                                                                    activity.creatorName,
                                                                    activity.creatorRole,
                                                                    activity.creatorPicture,
                                                                    activity.enrolled)
                                                            ))
                                                                showToastMessage(context, "Desinscrição realizada com sucesso!")
                                                            else
                                                                showToastMessage(context, "Erro ao realizar desinscrição!")

                                                        }
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(
                                                        0xFFDC1010
                                                    )
                                                    )
                                                ) {
                                                    Text("Sair", color = Color.Black)
                                                }
                                            }else{
                                                Button(
                                                    onClick = {
                                                        coroutineScope.launch {
                                                            activityViewModel.joinActivity(activity.id)
                                                        }
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14AE5C))
                                                ) {
                                                    Text("Aderir", color = Color.Black)
                                                }
                                            }
                                        }
                                    }
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}