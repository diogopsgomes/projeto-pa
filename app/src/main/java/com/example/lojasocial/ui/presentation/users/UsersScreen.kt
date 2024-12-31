package com.example.lojasocial.ui.presentation.users

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.showToastMessage

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(navController: NavHostController, usersViewModel: UsersViewModel){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val searchText by usersViewModel.searchText.collectAsState()

    LaunchedEffect(Unit) {
        usersViewModel.fetchUser(context)
    }

    val usersList by usersViewModel.listaUsers.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        bottomBar = {
            BottomBar(navController = navController, Route.Users)
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
                    text = "Utilizadores",
                    fontSize = 22.sp
                )
                SmallFloatingActionButton(
                    onClick = {
                        //Create new User
                        if(usersViewModel.isAdmin.value)
                            navController.navigate(Route.NewUser)
                        else
                            showToastMessage(context, "Não tem permissões suficientes para esta ação!")
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
                onValueChange = usersViewModel::onSearchTextChange,
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
                items(usersList) { user ->
                    Card(
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
                                        text = user.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = user.roleId,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
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
