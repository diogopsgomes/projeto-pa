package com.example.lojasocial.utils

import android.content.Context
import android.os.Build
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import com.example.lojasocial.domain.model.User
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    //object loading: AuthState()
    data class Error(
        val message: String
    ): AuthState()
}

fun showToastError(
    context: Context,
    e: Exception
) = makeText(context, "${e.message}", LENGTH_LONG).show()

fun showToastMessage(
    context: Context,
    message: String
) = makeText(context, "${message}", LENGTH_LONG).show()


fun guardarDadosUtilizador(context: Context, user: User): Boolean{
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString("userId", user.userId)
    editor.putString("email", user.email)
    editor.putString("name", user.name)
    editor.putString("surname", user.surname)
    editor.putString("picture", user.picture)
    editor.putString("roleId", user.roleId)

    return editor.commit()
}


fun getUserData(context: Context): User? {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val userId = sharedPreferences.getString("userId", null)
    val email = sharedPreferences.getString("email", null)
    val name = sharedPreferences.getString("name", null)
    val surname = sharedPreferences.getString("surname", null)
    val picture = sharedPreferences.getString("picture", null)
    val roleId = sharedPreferences.getString("roleId", null)

    return if (userId != null && email != null && name != null && surname != null && picture != null && roleId != null) {
        User(userId, email, name, surname, picture, roleId)
    } else {
        null
    }
}

fun clearUserData(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.edit().clear().commit()
}

fun formatDate(timestamp: Long): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormatter.format(Date(timestamp))
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateToTimeStamp(date: LocalDate): Long{
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun timestampToDate(timestamp: Long): LocalDate {
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getStartAndEndOfDay(date: LocalDate): Pair<Long, Long> {
    val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
    return Pair(startOfDay, endOfDay)
}