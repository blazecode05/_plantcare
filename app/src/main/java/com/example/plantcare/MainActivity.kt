package com.example.plantcare

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.App.AppNav
import com.example.plantcare.App.Routes
import com.example.plantcare.Pref.UserPreferences
import com.example.plantcare.ViewModel.PlantViewModel
import com.example.plantcare.ui.theme.PlantCareTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferences = UserPreferences(this)
        val viewModel: PlantViewModel by viewModels()
        setContent {
            PlantCareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    LaunchedEffect(Unit) {
                        val userId = userPreferences.getUserId()
                        if (userId != null) {
                            if (userId.isNotEmpty()) {
                                navController.navigate(Routes.BottomBar) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    }
                    AppNav(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        navController = navController,
                        userPreferences = userPreferences
                    )
                }
            }
        }
    }
}