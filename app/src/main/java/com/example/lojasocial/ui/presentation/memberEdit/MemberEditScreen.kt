package com.example.lojasocial.ui.presentation.memberEdit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.components.DropdownOutlinedTextField
import com.example.lojasocial.domain.model.Member
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberEditScreen(navController: NavHostController, memberEditViewModel: MemberEditViewModel, memberId: String?) {
    val options: List<String> = listOf("Option 1", "Option 2", "Option 3")
    var nationality by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf("") }
    val nationalities by memberEditViewModel.nationalities

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val visitas = "2" // check-ins
    val compras = "2" // check-outs com nota

    LaunchedEffect(Unit) {
        memberEditViewModel.getMemberById(memberId)
    }


    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        bottomBar = {
            BottomBar(navController = navController, Route.Membros)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ){
                Text(text = "Membro")
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        OutlinedTextField(
                            value = memberEditViewModel.name.value,
                            onValueChange = { memberEditViewModel.name.value = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Nome") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF6200EE),
                                unfocusedBorderColor = Color(0xFFBBBBBB),
                                containerColor = Color(0xFFF5F5F5)
                            )
                        )
                        OutlinedTextField(
                            value = memberEditViewModel.surname.value,
                            onValueChange = { memberEditViewModel.surname.value = it },
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
                        DropdownOutlinedTextField(labelText = "Alerta", options = listOf("Sim", "Não")
                            , modifier = Modifier.weight(1f), onValueChange = {
                                warning = it
                            })
                        OutlinedTextField(
                            value = memberEditViewModel.phone.value.toString(),
                            onValueChange = { memberEditViewModel.phone.value = it.toInt() },
                            modifier = Modifier.weight(1f),
                            label = { Text("Telefone") },
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
                            value = memberEditViewModel.locality.value,
                            onValueChange = { memberEditViewModel.locality.value = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Localidade") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF6200EE),
                                unfocusedBorderColor = Color(0xFFBBBBBB),
                                containerColor = Color(0xFFF5F5F5)
                            )
                        )

                        DropdownOutlinedTextField(labelText = "Nacionalidade", options = nationalities
                            , modifier = Modifier.weight(1f), onValueChange = { nationality = it})
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        OutlinedTextField(
                            value = memberEditViewModel.cc.value,
                            onValueChange = { memberEditViewModel.cc.value = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("CC / Passaporte") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF6200EE),
                                unfocusedBorderColor = Color(0xFFBBBBBB),
                                containerColor = Color(0xFFF5F5F5)
                            )
                        )
//                        OutlinedTextField(
//                            value = memberEditViewModel.warning.value,
//                            //onValueChange = { memberEditViewModel.warning.value = it },
//                            onValueChange = {  },
//                            modifier = Modifier.weight(1f),
//                            label = { Text(text = "Alerta") },
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                focusedBorderColor = Color(0xFF6200EE),
//                                unfocusedBorderColor = Color(0xFFBBBBBB),
//                                containerColor = Color(0xFFF5F5F5)
//                            )
//                        )
                    }
                    OutlinedTextField(
                        value = memberEditViewModel.notes.value,
                        onValueChange = { memberEditViewModel.notes.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Notas") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            unfocusedBorderColor = Color(0xFFBBBBBB),
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )
                }
                Column(){
                    Text("Atividade")
                    Text(
                        text = "$visitas Visitas | $compras Compras",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF49454F),
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(vertical = 15.dp)
                    )
                    Column(){ // vai ser lazy com a cenas dos items
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF4F0EF))
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ){
                            Column(){ // fazer com as cenas de items LazyColumn
                                Text(
                                    text = ZonedDateTime
                                        .now(ZoneId.of("Europe/Lisbon"))
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                                        .toString(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF49454F),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    text = "1 Camisola Criança, 1 Calças Criança" // notas check-out
                                )
                            }
                            Column(){
                                Text(
                                    text = ZonedDateTime
                                        .now(ZoneId.of("Europe/Lisbon"))
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                                        .toString(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF49454F),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    text = "1 Camisola Criança, 1 Calças Criança" // notas check-out
                                )
                            }
                        }
                    }
                }
                var aux = false
                if(warning == "Sim")
                    aux = true
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (memberId != null) {
                                if (memberEditViewModel.updateMember(
                                        Member(
                                            memberId,
                                            memberEditViewModel.name.value,
                                            memberEditViewModel.surname.value,
                                            memberEditViewModel.cc.value,
                                            memberEditViewModel.phone.value,
                                            memberEditViewModel.locality.value,
                                            memberEditViewModel.notes.value,
                                            aux,
                                            memberEditViewModel.checkedIn.value,
                                            memberEditViewModel.householdId.value,
                                            nationality
                                        )
                                    )
                                ) {
                                    showToastMessage(context, "Dados de membro atualizados com sucesso!")
                                } else {
                                    showToastMessage(context, "Erro ao atualizar os dados do membro!")
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
                ) {
                    Text("Guardar", color = Color(0xFF633B48))
                }
            }
        }
    }
}
