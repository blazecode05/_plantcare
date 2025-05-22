package com.example.plantcare.App

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.plantcare.Pref.UserPreferences
import com.example.plantcare.Screens.BottomBar
import com.example.plantcare.Screens.LoginUI
import com.example.plantcare.Screens.NewsApp
import com.example.plantcare.Screens.ProfileUI
import com.example.plantcare.Screens.SignUpUI
import com.example.plantcare.ViewModel.PlantViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNav(
    viewModel: PlantViewModel,
    modifier: Modifier,
    navController: NavHostController,
    userPreferences: UserPreferences
) {
    NavHost(navController = navController, startDestination = Routes.Login) {
        composable<Routes.Login> { LoginUI(navController=navController, userPreferences = userPreferences) }
        composable<Routes.SignUp> { SignUpUI(navController=navController, userPreferences = userPreferences) }
        composable<Routes.NewsUI> { NewsApp(navController = navController) }
        composable<Routes.BottomBar> { BottomBar(navController=navController, viewModel = viewModel, userPreferences = userPreferences) }
        composable<Routes.Profile> { ProfileUI() }
    }
}