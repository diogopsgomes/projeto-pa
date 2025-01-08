package com.example.lojasocial.ui.presentation.newActivity



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.components.BottomBar
import com.example.lojasocial.components.DatePickerModal
import com.example.lojasocial.domain.model.Activity
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.dateToTimeStamp
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewActivityScreen(navController: NavHostController, newActivityViewModel: NewActivityViewModel) {
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        newActivityViewModel.fetchUser(context)
    }

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
            Text(
                text = "Nova Atividade",
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(47.dp))

            OutlinedTextField(
                value = newActivityViewModel.title.value,
                onValueChange = { newActivityViewModel.title.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Título") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = newActivityViewModel.description.value,
                onValueChange = { newActivityViewModel.description.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Descrição") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = newActivityViewModel.local.value,
                onValueChange = { newActivityViewModel.local.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Local") },
                placeholder = {  },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color(0xFFBBBBBB),
                    containerColor = Color(0xFFF5F5F5)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))



            DatePickerField("Início",
                selectedDate = startDate,
                onDateSelected = { selected ->
                    startDate = selected
                    start = selected?.let { formatDate(it) } ?: ""
                }
            )


            Spacer(modifier = Modifier.height(15.dp))

            DatePickerField("Fim",
                selectedDate = endDate,
                onDateSelected = { selected ->
                    endDate = selected
                    end = selected?.let { formatDate(it) } ?: ""
                }
            )
            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick= {
                val str = dateToTimeStamp(LocalDate.now())

                if(newActivityViewModel.title.value != "" && newActivityViewModel.description.value != "" &&
                    newActivityViewModel.local.value != "" && startDate != null && endDate != null)
                    if( startDate!! < str)
                        showToastMessage(context, "Não é possivel criar uma ativadade com a data anterior a atual!")
                    else{
                        if(endDate!! < startDate!!)
                            showToastMessage(context, "A data final nao pode ser anterior a de inicio!")
                        else{
                            coroutineScope.launch {
                                if(newActivityViewModel.createActivity(Activity("",
                                        newActivityViewModel.title.value,
                                        newActivityViewModel.description.value,
                                        newActivityViewModel.local.value,
                                        startDate!!,
                                        endDate!!,
                                        newActivityViewModel.userId.value,
                                        newActivityViewModel.name.value,
                                        newActivityViewModel.role.value,
                                        newActivityViewModel.picture.value,
                                        0,
                                    )))
                                    showToastMessage(context, "Atividade adicionada com sucesso!")
                                else
                                    showToastMessage(context, "Erro ao adicionar Atividade!")
                            }
                        }

                    }
                else
                    showToastMessage(context, "Dados incompletos!")
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))) {
                Text("Criar", color = Color(0xFF633B48))
            }

        }
    }
}


// Função para formatar o timestamp como uma String legível
fun formatDate(timestamp: Long): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormatter.format(Date(timestamp))
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    // Ícone de calendário
    val calendarIcon = Icons.Default.DateRange

    OutlinedTextField(
        value = selectedDate?.let { formatDate(it) } ?: "",
        onValueChange = { /* Não permitir edição manual */ },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        label = { Text(label) },
        placeholder = { Text("Selecione uma data") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF6200EE),
            unfocusedBorderColor = Color(0xFFBBBBBB),
            containerColor = Color(0xFFF5F5F5)
        ),
        readOnly = true, // Impede edição manual
        enabled = true, // Mantém o TextField habilitado
        trailingIcon = {
            Icon(
                imageVector = calendarIcon,
                contentDescription = "Selecionar data",
                modifier = Modifier
                    .clickable { showDatePicker = true } // Abre o DatePicker ao clicar no ícone
                    .padding(8.dp) // Adiciona um padding para facilitar o clique
            )
        }
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { date ->
                onDateSelected(date)
                showDatePicker = false // Fecha o modal após selecionar
            },
            onDismiss = { showDatePicker = false } // Fecha o modal ao cancelar
        )
    }
}
