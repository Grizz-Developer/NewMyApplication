package com.guit.edu.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guit.edu.myapplication.ui.LoginScreen
import com.guit.edu.myapplication.ui.RegisterScreen
import com.guit.edu.myapplication.ui.UserScreen
import com.guit.edu.myapplication.ui.theme.MyApplicationTheme
import com.guit.edu.myapplication.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen()
        }
    }
}

@Composable
fun AppScreen(){
    val navController = rememberNavController()
    val context = LocalContext.current
    var token by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        DataStoreUtil.getToken(context).collect {
            token = it
        }
    }

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("register") { RegisterScreen(navController) }
                composable("main") {
                    val userViewModel: UserViewModel = viewModel()
                    userViewModel.fetchUserInfo(token)
                    UserScreen(viewModel = userViewModel)
                }
            }
        }
    }
}


