package com.guit.edu.myapplication.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.guit.edu.myapplication.entity.History
import com.guit.edu.myapplication.entity.User
import com.guit.edu.myapplication.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneId
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun UserScreen(viewModel: UserViewModel) {
    val user by viewModel.user.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var showHistoryDialog by remember { mutableStateOf(false) }
    if (showEditDialog) {
        EditDialog(user = user, onDismiss = { showEditDialog = false }, viewModel = viewModel)
    }
    if (showHistoryDialog) {
        user?.let {
//            HistoryDialog(
//                username = it.username,
//                onDismiss = { showHistoryDialog = false },
//                viewModel = viewModel
//            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            model = "https://avatars.githubusercontent.com/u/69185151?v=4",
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        user?.let {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户名: ${it.username}", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "修改")
                }
            }

            Text(text = "昵称: ${it.nickname}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "性别: ${it.gender}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "签名: ${it.signature}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "身高: ${it.height}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "体重: ${it.weight}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "杯子容量: ${it.cupcapacity}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "饮水任务: ${it.assignment}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showHistoryDialog = true }) {
                Text(text = "查看历史饮水记录")
            }
        }
    }
}

@Composable
fun EditDialog(user: User?, onDismiss: () -> Unit, viewModel: UserViewModel) {
    val context = LocalContext.current
    var nickname by remember { mutableStateOf(user?.nickname ?: "") }
    var gender by remember { mutableStateOf(user?.gender ?: "") }
    var signature by remember { mutableStateOf(user?.signature ?: "") }
    var height by remember { mutableStateOf(user?.height?.toString() ?: "") }
    var weight by remember { mutableStateOf(user?.weight?.toString() ?: "") }
    var cupcapacity by remember { mutableStateOf(user?.cupcapacity?.toString() ?: "") }
    var assignment by remember { mutableStateOf(user?.assignment?.toString() ?: "") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("昵称") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("性别") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = signature,
                onValueChange = { signature = it },
                label = { Text("签名") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("身高") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("体重") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cupcapacity,
                onValueChange = { cupcapacity = it },
                label = { Text("杯子容量") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = assignment,
                onValueChange = { assignment = it },
                label = { Text("饮水任务") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    val updateUser = User(
                        id = user?.id ?: 0,
                        username = user?.username ?: "",
                        nickname = nickname,
                        gender = gender,
                        signature = signature,
                        height = height.toIntOrNull(),
                        weight = weight.toIntOrNull(),
                        cupcapacity = cupcapacity.toIntOrNull(),
                        assignment = assignment.toIntOrNull()
                    )
                    viewModel.updateUserInfo(updateUser)
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }) {
                    Text("确认")
                }
            }
        }
    }
}

//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun HistoryDialog(username: String, onDismiss: () -> Unit, viewModel: UserViewModel) {
//    val history by viewModel.history.collectAsState()
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
//    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//    val datePickerState = rememberDatePickerState()
//
//    val datePickerDialog = remember {
//        DatePickerDialog(
//            onDismissRequest = onDismiss,
//            state = datePickerState,
//            confirmButton = {
//                TextButton(onClick = {
//                    datePickerState.selectedDateMillis?.let {
//                        selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
//                            .toLocalDate()
//                        viewModel.fetchUserHistory(username, selectedDate.toString())
//                        onDismiss()
//                    }
//                }) {
//                    Text("确认")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = onDismiss) {
//                    Text("取消")
//                }
//            }
//        )
//    }
//
//
//    LaunchedEffect(key1 = selectedDate) {
//        viewModel.fetchUserHistory(username, selectedDate.toString())
//    }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("饮水记录") },
//        content = {
//            Column {
//                Button(onClick = { datePickerDialog.show() }) {
//                    Text(text = "选择日期")
//                }
//                history?.let {
//                    if (it.isEmpty()) {
//                        Text("暂无数据")
//                    } else {
//                        LazyColumn {
//                            items(it.size) { index ->
//                                val historyItem = it[index]
//                                Text(
//                                    text = "时间:${
//                                        historyItem.createdAt?.let { simpleDateFormat.format(it) }
//                                    },饮用量:${historyItem.drink},类型${historyItem.type}"
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = onDismiss) {
//                Text("关闭")
//            }
//        }
//    )
//}
@Preview
@Composable
fun PreviewUserScreen() {
    val viewModel : UserViewModel = viewModel()
    UserScreen(viewModel = viewModel)
}
