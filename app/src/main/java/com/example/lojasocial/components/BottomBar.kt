package com.example.lojasocial.components

import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.R
import com.example.lojasocial.navigation.Route
import java.time.LocalDateTime



@Composable
fun BottomBar(navController: NavHostController, atual: Route) {
    var screenDashboard by remember { mutableStateOf(false) }
    var screenMembros by remember { mutableStateOf(false) }
    var screenAtividades by remember { mutableStateOf(false) }
    var screenUtilizadores by remember { mutableStateOf(false) }
    var screenPerfil by remember { mutableStateOf(false) }

    when(atual){
        Route.Dashboard -> screenDashboard = true
        Route.Membros -> screenMembros = true
        Route.Atividades -> screenAtividades = true
        Route.Users -> screenUtilizadores = true
        Route.Profile -> screenPerfil = true
        else -> Unit
    }

    BottomNavigation(
        backgroundColor = Color(0xFFFFFFFF),
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        elevation = 0.dp
    ) {
        BottomNavigationItem(
            selected = screenDashboard,
            onClick = {
                screenDashboard = true
                screenMembros = false
                screenAtividades = false
                screenUtilizadores = false
                screenPerfil = false
                navController.navigate(Route.Dashboard) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (screenDashboard) Color(0xFFFFD8E4) else Color.Transparent
                        )
                        .padding(horizontal = 16.dp),
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Inicio",
                    tint = Color(0xFF4A4459)
                )
            },
            label = {
                Text(
                    text = "Inicio",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4459)
                    )
                )
            },
            selectedContentColor = Color(0xFF4A4459),
            unselectedContentColor = Color(0xFF4A4459),
            modifier = Modifier.weight(1f))
        //
        BottomNavigationItem(
            selected = screenMembros,
            onClick = {
                screenDashboard = false
                screenMembros = true
                screenAtividades = false
                screenUtilizadores = false
                screenPerfil = false
                navController.navigate(Route.Membros) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (screenMembros) Color(0xFFFFD8E4) else Color.Transparent
                        )
                        .padding(horizontal = 16.dp),
                    painter = painterResource(id = R.drawable.ic_members),
                    contentDescription = "Membros",
                    tint = Color(0xFF4A4459)
                )
            },
            label = {
                Text(
                    text = "Membros",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4459)
                    )
                )
            },
            selectedContentColor = Color(0xFF4A4459),
            unselectedContentColor = Color(0xFF4A4459),
            modifier = Modifier.weight(1f))
        //
        BottomNavigationItem(
            selected = screenAtividades ,
            onClick = {
                screenDashboard = false
                screenMembros = false
                screenAtividades = true
                screenUtilizadores = false
                screenPerfil = false
                navController.navigate(Route.Activity) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (screenAtividades) Color(0xFFFFD8E4) else Color.Transparent
                        )
                        .padding(horizontal = 16.dp),
                    painter = painterResource(id = R.drawable.ic_activities),
                    contentDescription = "Atividades",
                    tint = Color(0xFF4A4459)
                )
            },
            label = {
                Text(
                    text = "Atividades",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4459)
                    )
                )
            },
            selectedContentColor = Color(0xFF4A4459),
            unselectedContentColor = Color(0xFF4A4459),
            modifier = Modifier.weight(1f))
        //
        BottomNavigationItem(
            selected = screenUtilizadores,
            onClick = {
                screenDashboard = false
                screenMembros = false
                screenAtividades = false
                screenUtilizadores = true
                screenPerfil = false
                navController.navigate(Route.Users) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (screenUtilizadores) Color(0xFFFFD8E4) else Color.Transparent
                        )
                        .padding(horizontal = 16.dp),
                    painter = painterResource(id = R.drawable.ic_users),
                    contentDescription = "Utilizadores",
                    tint = Color(0xFF4A4459)
                )
            },
            label = {
                Text(
                    text = "Utilizadores",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4459)
                    )
                )
            },
            selectedContentColor = Color(0xFF4A4459),
            unselectedContentColor = Color(0xFF4A4459),
            modifier = Modifier.weight(1f))
        //
        BottomNavigationItem(
            selected = screenPerfil,
            onClick = {
                screenDashboard = false
                screenMembros = false
                screenAtividades = false
                screenUtilizadores = false
                screenPerfil = true
                navController.navigate(Route.Profile) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (screenPerfil) Color(0xFFFFD8E4) else Color.Transparent
                        )
                        .padding(horizontal = 16.dp),
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Perfil",
                    tint = Color(0xFF4A4459)
                )
            },
            label = {
                Text(
                    text = "Perfil",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A4459)
                    )
                )
            },
            selectedContentColor = Color(0xFF4A4459),
            unselectedContentColor = Color(0xFF4A4459),
            modifier = Modifier.weight(1f))
    }

}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
