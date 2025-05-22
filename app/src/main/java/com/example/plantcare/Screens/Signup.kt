package com.example.plantcare.Screens

import com.example.plantcare.App.Routes
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantcare.AuthApi.RetrofitInstance
import com.example.plantcare.Pref.UserPreferences
import com.example.plantcare.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Preview(showBackground = true)
@Composable
fun SignUpUI(modifier: Modifier = Modifier, navController: NavHostController, userPreferences: UserPreferences) {
    val context= LocalContext.current
    val coroutineScope= rememberCoroutineScope()

    val name= remember { mutableStateOf("") }
    val phone= remember { mutableStateOf("") }
    val email= remember { mutableStateOf("") }
    val password= remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(
                    color = Color(
                        0xCBFFFFFF
                    )
                )
                .align(Alignment.BottomCenter)
                .padding(horizontal = 15.dp, vertical = 6.dp)
        ) {
            Text(text = "Hello\nLet's create your account", fontSize = 27.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(value = name.value, onValueChange = {name.value=it},
                label = { Text(text = "Your name", color = Color(0xFF00695C),)},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00695C),  // Border color when focused
                    focusedLabelColor = Color(0xFF00695C),  // Label color when focused
                    cursorColor = Color(0xFF00695C),
                    focusedTextColor = Color(0xFF00695C),
                    unfocusedTextColor = Color(0xFF00695C),
                ),
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = phone.value, onValueChange = {phone.value=it},
                label = { Text(text = "Your phone number", color = Color(0xFF00695C),)},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00695C),  // Border color when focused
                    focusedLabelColor = Color(0xFF00695C),  // Label color when focused
                    cursorColor = Color(0xFF00695C),
                    focusedTextColor = Color(0xFF00695C),
                    unfocusedTextColor = Color(0xFF00695C),
                ),
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = email.value, onValueChange = {email.value=it},
                label = { Text(text = "Your email", color = Color(0xFF00695C),)},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00695C),  // Border color when focused
                    focusedLabelColor = Color(0xFF00695C),  // Label color when focused
                    cursorColor = Color(0xFF00695C),
                    focusedTextColor = Color(0xFF00695C),
                    unfocusedTextColor = Color(0xFF00695C),
                ),
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = password.value, onValueChange = {password.value=it},
                label = { Text(text = "Your password", color = Color(0xFF00695C),)},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00695C),  // Border color when focused
                    focusedLabelColor = Color(0xFF00695C),  // Label color when focused
                    cursorColor = Color(0xFF00695C),
                    focusedTextColor = Color(0xFF00695C),
                    unfocusedTextColor = Color(0xFF00695C),
                ),
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = {
                coroutineScope.launch {
                    try {
                        val response = RetrofitInstance.api.signUp(name = name.value,email=email.value, password = password.value, phone = phone.value)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val userId = response.body()?.message ?: ""
                                userPreferences.saveUserId(userId)

                                Toast.makeText(context, "SignUp Successful!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Routes.BottomBar) {
                                    popUpTo(Routes.Login) { inclusive = true }  // Clear back stack
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val errorMessage = try {
                                    JSONObject(errorBody ?: "").getString("message")
                                } catch (e: Exception) {
                                    "SignUp Failed! Check credentials."
                                }
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
                modifier=Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF00695C)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(3.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text(text = "Already have an account?", fontSize = 15.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Sign In", fontSize = 15.sp, color = Color(0xFF00695C), fontWeight = FontWeight.Bold, modifier=Modifier.clickable { navController.navigate(
                    Routes.Login) })
            }
        }
    }
}