package com.example.plantcare.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantcare.NavBarItem
import com.example.plantcare.Pref.UserPreferences
import com.example.plantcare.R
import com.example.plantcare.ViewModel.PlantViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: PlantViewModel,
    userPreferences: UserPreferences
) {
    var selectedIndex = remember { mutableIntStateOf(0) }

    val item = listOf(
        NavBarItem("News", R.drawable.newspaper),
        NavBarItem("Plant Disease", R.drawable.monstera)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF00E926), // Green color
                modifier = Modifier.height(100.dp).border(2.dp, Color(0xFF00695C))
            ) {
                item.forEachIndexed { index, navBarItem ->
                    NavigationBarItem(
                        selected = selectedIndex.intValue == index,
                        onClick = { selectedIndex.value = index },
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = navBarItem.icon),
                                    contentDescription = "Icon",
                                    modifier = Modifier.size(24.dp), // Slightly bigger icon
                                    colorFilter = ColorFilter.tint(
                                        if (selectedIndex.intValue == index) Color.White else Color.Black
                                    )
                                )
                                Text(
                                    text = navBarItem.name,
                                    fontSize = 12.sp, // Slightly bigger text
                                    color = if (selectedIndex.intValue == index) Color.White else Color.Black
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White.copy(alpha = 0.3f) // Subtle highlight effect
                        ),
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedIndex.value) {
                0 -> NewsApp(navController = navController)
                1 -> PlantDisease(viewModel = viewModel, userPreferences = userPreferences, navController=navController)
            }
        }
    }
}