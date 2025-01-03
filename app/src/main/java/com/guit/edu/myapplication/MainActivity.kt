package com.guit.edu.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val userViewModel : UserViewModel = viewModel()
            // TODO: get user token
            userViewModel.fetchUserInfo("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsInVzZXJuYW1lIjoidGVzdCIsImlhdCI6MTcwMjY0NDU1NCwiZXhwIjoxNzAyNzMwOTU0fQ.Mh2f8N8f1l5z13m06s77H1Xz0N-r1q2P1t1-mXQz94")
            UserScreen(viewModel = userViewModel)
        }
    }
}