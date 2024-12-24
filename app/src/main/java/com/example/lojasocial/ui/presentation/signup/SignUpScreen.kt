package com.example.lojasocial.ui.presentation.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.R
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.AuthState
import com.example.lojasocial.utils.guardarDadosUtilizador
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surename by remember { mutableStateOf("") }
    var roleId by remember { mutableStateOf("") }


    val coroutineScope = rememberCoroutineScope()
    val authState = signUpViewModel.authState.observeAsState()
    val context = LocalContext.current

    var user: User? = null

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Route.Dashboard) //navController.navigate(Route.Home(user!!.email))
            is AuthState.Error -> showToastMessage(context,(authState.value as AuthState.Error).message)
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment =  Alignment.CenterHorizontally
    ){

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logotipo",
            modifier = Modifier
                .size(width = 600.dp, height = 350.dp)
                .padding(top = 60.dp, bottom = 80.dp))

        Text(text = "Registo",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.W400
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )


        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Senha") },
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )



        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = surename,
            onValueChange = { surename = it },
            label = { Text("Apelido") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        //Implementar depois com enum ou sealed class
//        TextField(
//            value = roleId,
//            onValueChange = { roleId = it },
//            label = { Text("Email") },
//            modifier = Modifier.padding(bottom = 8.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(
//                imeAction = ImeAction.Next
//            )
//        )


        Button(onClick = {
            if(password == confirmPassword){
                coroutineScope.launch {
                    try {
                        user = signUpViewModel.register(email, password, name, surename, roleId,context)
                    }catch (e: Exception){
                        Log.d("Teste", "teste")
                    }
                }
            }else{
                showToastMessage(context, "Password must match ConfirmPassword")
            }
        },colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
        ) {
            Text("Criar Conta", color = Color(0xFF633B48))
        }

        // Navegação para a tela de Login
        Button(
            onClick = { navController.navigate(Route.Login) },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
        ) {
            Text("Já tem conta? Inicie sessão", color = Color(0xFF633B48))
        }
    }
}