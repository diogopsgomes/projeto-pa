package com.example.lojasocial.ui.presentation.member

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberScreen(navController: NavHostController, memberViewModel: MemberViewModel){
    val membersList by memberViewModel.listaMembros.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val searchText by memberViewModel.searchText.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFFFFF), // Define a cor de fundo diretamente no Scaffold
        bottomBar = {
            BottomBar(navController = navController, Route.Membros)
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
                    text = "Membros",
                    fontSize = 22.sp
                )
                SmallFloatingActionButton(
                    onClick = {
                        navController.navigate(Route.NewMember)
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
                onValueChange =  memberViewModel::onSearchTextChange ,
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
                items(membersList) { member ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        shape = MaterialTheme.shapes.medium,
                        onClick = { navController.navigate(Route.MemberEdit(member.id)) }
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
                                    Text(
                                        text = member.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = member.phone.toString(),
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                                if(member.checkedIn){
                                    Button(
                                        onClick = {
                                            navController.navigate(Route.CheckOut(member.id))
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(
                                            text = "Check-out",
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                    }
                                }else{
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                if(memberViewModel.CreateCheckIn(member))
                                                    showToastMessage(context, "CheckIn Registado Com Sucesso!")
                                                else
                                                    showToastMessage(context, "Erro ao registar CheckIn")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(
                                            text = "Check-In",
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
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