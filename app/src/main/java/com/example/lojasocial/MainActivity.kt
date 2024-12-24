package com.example.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.lojasocial.navigation.SetupNavGraph
import com.example.lojasocial.ui.presentation.loading.LoadingViewModel
import com.example.lojasocial.ui.theme.LojaSocialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LojaSocialTheme {
                val loadingViewModel: LoadingViewModel by viewModels()
                SetupNavGraph(loadingViewModel)
            }
        }
    }
}
