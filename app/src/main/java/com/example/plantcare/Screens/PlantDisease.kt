package com.example.plantcare.Screens

import com.example.plantcare.App.Routes
import com.example.plantcare.Pref.UserPreferences
import com.example.plantcare.R
import com.example.plantcare.ViewModel.PlantViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PlantDisease(
    modifier: Modifier = Modifier,
    viewModel: PlantViewModel,
    userPreferences: UserPreferences,
    navController: NavHostController
) {
    val context = LocalContext.current
    val contentResolver = LocalContext.current.contentResolver
    val mimeTypeFilter = arrayOf("image/jpeg", "image/png")
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val result = viewModel.plantDiseaseResult.collectAsState().value

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (Build.VERSION.SDK_INT < 28) {
            imageBitmap.value = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(contentResolver, uri!!)
            imageBitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .background(
                        Color(0xFF00695C),
                        RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                    )
                    .border(
                        2.dp,
                        Color(0xFF00E926),
                        RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                    )
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Plant Care Screen",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF00E926),
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageBitmap.value == null) {
                    Card(
                        modifier = Modifier
                            .size(200.dp)
                            .clickable { imagePickerLauncher.launch(mimeTypeFilter) },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color(0xFF00695C)),
                        colors = CardDefaults.cardColors(Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.add_image),
                                contentDescription = "upload",
                                modifier = Modifier.size(50.dp),
                                colorFilter = ColorFilter.tint(Color.Gray)
                            )
                        }
                    }
                }

                imageBitmap.value?.let { uri ->
                    Card(
                        modifier = Modifier
                            .size(200.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color(0xFF00695C)),
                        colors = CardDefaults.cardColors(Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = BitmapPainter(
                                    image = imageBitmap.value!!.asImageBitmap()
                                ),
                                contentDescription = "Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Image(painter = painterResource(id = R.drawable.plant), contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF00695C)),
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            imageBitmap.value?.let { viewModel.plantDiseaseResult(it) }
                            Toast
                                .makeText(
                                    context,
                                    "If you're not satisfied with this result, \nGenerate again.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        })
                Spacer(modifier = Modifier.height(20.dp))
                // Displaying the generated story or a default message.
                result?.let {
                    Text(text = it, color = Color.Black)
                }

            }
        }
        FloatingActionButton(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        userPreferences.saveUserId("") // Clearing User ID for logout
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Logout Successful!", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(Routes.Login) {
                                popUpTo(Routes.BottomBar) { inclusive = true }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Logout Failed! Try Again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .height(100.dp)
                .width(150.dp)
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            containerColor = Color(0xFF00695C),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}