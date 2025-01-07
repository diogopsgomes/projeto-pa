package com.example.lojasocial.ui.presentation.teste

import android.os.Build
import android.text.format.DateUtils.formatDateTime
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lojasocial.data.remote.model.ActivityDto
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.ui.presentation.dashboard.DashboardViewModel
import com.example.lojasocial.utils.AuthState
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun testeScreen(navController: NavHostController, testeViewModel: TesteViewModel){

    // Estados para os campos de entrada
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDateTime.now()) }
    var endDate by remember { mutableStateOf(LocalDateTime.now()) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Campo para o título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para a descrição
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para a localidade
        OutlinedTextField(
            value = locality,
            onValueChange = { locality = it },
            label = { Text("Localidade") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para a data e hora de início
        Text("Data e Hora de Início: ${formatDateTime(startDate)}")
        Button(
            onClick = {
                // Abrir um DatePicker e TimePicker para selecionar a data e hora de início
                // (Implemente essa lógica)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Selecionar Data e Hora de Início")
        }

        // Campo para a data e hora de término
        Text("Data e Hora de Término: ${formatDateTime(endDate)}")
        Button(
            onClick = {
                // Abrir um DatePicker e TimePicker para selecionar a data e hora de término
                // (Implemente essa lógica)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Selecionar Data e Hora de Término")
        }

        // Botão para criar a atividade
        Button(
            onClick = {

//                // Criar o objeto ActivityDto
//                val activity = Activity(
//                    id = "",
//                    title = title,
//                    description = description,
//                    locality = locality,
//                    startDate = startDate,
//                    endDate = endDate
//                )
//                coroutineScope.launch {
//                    testeViewModel.createActivity(activity)
//                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Atividade")
        }
    }

}

// Função para formatar LocalDateTime como String
@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return dateTime.format(formatter)
}