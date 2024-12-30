package com.example.lojasocial.ui.presentation.newMember

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.components.DropdownOutlinedTextField
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMemberScreen(navController: NavHostController, newMemberViewModel: NewMemberViewModel) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var cc by remember { mutableStateOf("") }
    val nationalities by newMemberViewModel.nationalities

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFFFFFFFF),
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
            Text(
                text = "Novo Membro",
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(47.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Nome") },
                placeholder = {  },
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
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Apelido") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                label = { Text("E-mail") },
//                placeholder = {  },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color(0xFF6200EE),
//                    unfocusedBorderColor = Color(0xFFBBBBBB),
//                    containerColor = Color(0xFFF5F5F5)
//                )
//            )
//            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Telefone") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = locality,
                onValueChange = { locality = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Localidade") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            DropdownOutlinedTextField(labelText = "Nacionalidade", options = nationalities
                , modifier = Modifier.fillMaxWidth(), onValueChange = {nationality = it})

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = cc,
                onValueChange = { cc = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Cartão de Cidadão / Passaporte") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            // Pesquisa

            var searchText by remember { mutableStateOf("") }

            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text("Procura de Familiar") },
                placeholder = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ícone de pesquisa"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF4F0EF),
                    focusedIndicatorColor = Color.Transparent, // Remove a borda ao focar
                    unfocusedIndicatorColor = Color.Transparent // Remove a borda quando não estiver focado
                )
            )
            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick= {
                //Create Member
                if(name != "" && surname != "" && phone != "" && locality != "" && nationality != "" && cc != "")
                    coroutineScope.launch {
                        if(newMemberViewModel.createMember(Member("",name,surname, cc, phone.toInt(), locality, "", false, false, "", nationality)))
                            showToastMessage(context, "Membro adicionado com sucesso!")
                        else
                            showToastMessage(context, "Erro ao adicionar Membro!")
                    }
                    else
                        showToastMessage(context, "dados incompletos!")
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))) {
                Text("Criar", color = Color(0xFF633B48))
            }

        }
    }
}