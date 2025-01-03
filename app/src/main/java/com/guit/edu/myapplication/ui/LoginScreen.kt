package com.guit.edu.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.guit.edu.myapplication.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.guit.edu.myapplication.net.Result

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by viewModel.loginResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("登录", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("用户名") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("密码") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = { viewModel.login(username, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("登录")
        }

        TextButton(onClick = { navController.navigate("register")}) {
            Text("没有账号？去注册")
        }

        // Handle Login Result
        when (loginResult) {
            is Result.Success -> {
                // Navigate to main screen after successful login
                LaunchedEffect(Unit){
                    navController.navigate("main"){
                        popUpTo("login"){
                            inclusive = true
                        }
                    }
                }

            }

            is Result.Error -> {
                Text("登陆失败:" + (loginResult as? Result.Error)?.message, color = MaterialTheme.colorScheme.error)
            }

            is Result.Loading -> {
                CircularProgressIndicator()
            }
            else ->{}
        }
    }
}
