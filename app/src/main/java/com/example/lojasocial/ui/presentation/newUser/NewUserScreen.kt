package com.example.lojasocial.ui.presentation.newUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch
import com.example.lojasocial.components.DropdownOutlinedTextField as DropdownOutlinedTextField1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewUserScreen(navController: NavHostController, newUserViewModel: NewUserViewModel) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    val roles by newUserViewModel.roles
    val roleNames = roles.map { it.description }

    var selectedRole by remember { mutableStateOf("")}

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
            Text(
                text = "Novo Utilizador",
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(47.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 150.dp)
                ) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nome") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = surname,
                        onValueChange = { surname = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Apelido") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("E-mail") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Palavra-passe") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    DropdownOutlinedTextField1(
                        labelText = "Tipo", options = roleNames, modifier = Modifier.weight(1f), onValueChange = {selectedRole = it}
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {
                            //Create User
                            if(name != "" && surname != "" && email != "" && password != "" && selectedRole != "")
                                coroutineScope.launch {
                                    if(newUserViewModel.createUser(User("",email,name, surname, "",selectedRole), password))
                                        showToastMessage(context, "Utilizador Criado com sucesso!")
                                    else
                                        showToastMessage(context, "Erro ao criar utilizador!")
                                }
                            else
                                showToastMessage(context, "Todos os Campos são obrigatórios!")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
                    ) {
                        Text("Criar", color = Color(0xFF633B48))
                    }
                }
            }
        }
    }
}
