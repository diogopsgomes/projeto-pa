package com.example.lojasocial.ui.presentation.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Log.d
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.navigation.Route
import kotlinx.coroutines.launch



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var cc by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (profileViewModel.userId.value != null) {
                imageUri?.let {
                    profileViewModel.uploadAndSaveImage(profileViewModel.userId.value, it)
                    profileViewModel.picture.value = imageUri.toString()
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        profileViewModel.fetchUser(context)
    }




    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        bottomBar = {
            BottomBar(navController = navController, Route.Profile)
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
                    text = "Perfil",
                    fontSize = 22.sp
                )
                SmallFloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                profileViewModel.logout(context)
                                navController.navigate(Route.Login)
                            }catch (e: Exception){
                                d("Teste", "teste")
                            }
                        }
                    },
                    modifier = Modifier.shadow(
                        elevation = 15.dp,
                        shape = FloatingActionButtonDefaults.smallShape
                    ),
                    containerColor = Color(0xFFF4F0EF),
                    contentColor = Color(0xFF49454F),
                )
                {
                    Icon(Icons.Filled.ExitToApp, "Sair")
                }

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                AsyncImage(
                    model = profileViewModel.picture.value.toString(),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(Intent.ACTION_PICK).apply {
                                type = "image/*"
                            }
                            launcher.launch(intent)
                        },
                    contentScale = ContentScale.Crop,
                    placeholder = rememberVectorPainter(image = Icons.Filled.AccountCircle),
                    error = rememberVectorPainter(image = Icons.Filled.AccountCircle),
                )
                Text(profileViewModel.name.value)
                Text(profileViewModel.role.value)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    OutlinedTextField(
                        value = profileViewModel.name.value,
                        onValueChange = { profileViewModel.name.value = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Nome") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )
                    OutlinedTextField(
                        value = profileViewModel.surname.value,
                        onValueChange = { profileViewModel.surname.value = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Apelido") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    OutlinedTextField(
                        value = profileViewModel.email.value,
                        onValueChange = { profileViewModel.email.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("E-mail") },
                        enabled = false,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )
                }
                OutlinedTextField(
                    value = profileViewModel.role.value,
                    onValueChange = { profileViewModel.role.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Tipo") },
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6200EE),
                        unfocusedBorderColor = Color(0xFFBBBBBB),
                        containerColor = Color(0xFFF5F5F5)
                    )
                )

            }
                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick= {
                    coroutineScope.launch {
                        try {
                            profileViewModel.updateUser(context)
                        }catch (e: Exception){
                            d("Teste", "teste")
                        }
                    }
                }, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))) {
                    Text("Guardar", color = Color(0xFF633B48))
                }
            }
        }
    }


fun pickImage(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }
    launcher.launch(intent)
}
