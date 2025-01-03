package com.guit.edu.myapplication.ui

import android.util.Log
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
import com.guit.edu.myapplication.entity.LoginResult
import com.guit.edu.myapplication.net.Result
import androidx.compose.material3.Checkbox
import com.guit.edu.myapplication.DataStoreUtil
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.first

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by viewModel.loginResult.collectAsState()
    var rememberPassword by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        username = DataStoreUtil.getUsername(context).first()
        rememberPassword = DataStoreUtil.getRememberPassword(context).first()
        if(rememberPassword){
            password = DataStoreUtil.getPassword(context).first()
        }
    }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = rememberPassword,
                onCheckedChange = { rememberPassword = it },
            )
            Text(text = "记住密码")
        }

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
                val loginSuccess = loginResult as Result.Success<LoginResult>
                LaunchedEffect(Unit){
                    if (!loginSuccess.data.data.isNullOrEmpty()) {
                        viewModel.saveToken(loginSuccess.data.data)
                        Log.d("LoginScreen", "Token saved: ${loginSuccess.data.data}")
                        viewModel.saveRememberPasswordAndPwd(rememberPassword, password)
                        viewModel.saveUsername(username)
                        navController.navigate("main") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    } else {
                        Log.e("LoginScreen", "Token is null or empty")
                        // 可以选择显示一个错误信息给用户
                        // 例如：
                        navController.navigate("main") {
                            popUpTo("login") {
                                inclusive = true
                            }
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
