package com.guit.edu.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.guit.edu.myapplication.R
import com.guit.edu.myapplication.entity.User
import com.guit.edu.myapplication.ui.theme.ButtonColor
import com.guit.edu.myapplication.viewmodel.UserViewModel
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import androidx.compose.material3.DatePicker as Material3DatePicker
import androidx.compose.material3.DatePickerDialog as Material3DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.style.TextAlign


@Composable
fun UserScreen(viewModel: UserViewModel) {
    val user by viewModel.user.collectAsState()

    var showNicknameDialog by remember { mutableStateOf(false) }
    var showSignatureDialog by remember { mutableStateOf(false) }

    var showGenderPicker by remember { mutableStateOf(false) }
    var showHeightPicker by remember { mutableStateOf(false) }
    var showWeightPicker by remember { mutableStateOf(false) }
    var showCupCapacityPicker by remember { mutableStateOf(false) }
    var showAssignmentPicker by remember { mutableStateOf(false) }
    var showHistoryDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.panda), // Replace with your image
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Nickname
        Text(
            text = user?.nickname ?: "点击设置昵称",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showNicknameDialog = true },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )


        // Signature
        Text(
            text = user?.signature ?: "点击设置个性签名",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showSignatureDialog = true },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Display the user's info in rows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            // Gender Button
            Button(onClick = { showGenderPicker = true }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("性别")
                    Text(user?.gender ?: "未选择", color = Color.Black)

                }
            }
            // Height Button
            Button(onClick = { showHeightPicker = true }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("身高")
                    Text("${user?.height ?: 0}cm", color = Color.Black)
                }
            }
            // Weight Button
            Button(onClick = { showWeightPicker = true }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("体重")
                    Text("${user?.weight ?: 0}kg", color = Color.Black)
                }
            }
            // Cup Capacity Button
            Button(onClick = { showCupCapacityPicker = true }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("杯容量")
                    Text("${user?.cupcapacity ?: 0}ml", color = Color.Black)
                }
            }

        }


        // Daily Assignment Button
        Button(onClick = { showAssignmentPicker = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "每日目标", modifier = Modifier.weight(1f), color = Color.Black)
                Text(text = "${user?.assignment ?: 0}ml", color = Color.Black)
            }
        }



        // Other buttons from the image
        Button(onClick = { showHistoryDialog = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "我的打卡", color = Color.Black)
        }

        Button(onClick = { /* Handle action */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "含水量表", color = Color.Black)
        }
        Button(onClick = { /* Handle action */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "关于我们", color = Color.Black)
        }

        Button(onClick = { /* Handle action */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "修改密码", color = Color.Black)
        }
        Button(onClick = { /* Handle action */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "当前版本 1.0", color = Color.Black)
        }

        Button(onClick = { /* Handle action */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
            Text(text = "退出登录", color = Color.Black)
        }
    }
    // Pickers
    if (showGenderPicker) {
        GenderPickerDialog(onDismiss = { showGenderPicker = false }, onGenderSelected = { gender ->
            viewModel.updateGender(gender)
        })
    }

    if (showHeightPicker) {
        NumberPickerDialog(
            title = "选择身高",
            onDismiss = { showHeightPicker = false },
            onValueSelected = { value -> viewModel.updateHeight(value) },
            minValue = 100,
            maxValue = 250,
            initValue = user?.height ?: 170
        )
    }

    if (showWeightPicker) {
        NumberPickerDialog(
            title = "选择体重",
            onDismiss = { showWeightPicker = false },
            onValueSelected = { value -> viewModel.updateWeight(value) },
            minValue = 30,
            maxValue = 200,
            initValue = user?.weight ?: 70
        )
    }

    if (showCupCapacityPicker) {
        NumberPickerDialog(
            title = "选择杯容量",
            onDismiss = { showCupCapacityPicker = false },
            onValueSelected = { value -> viewModel.updateCupCapacity(value) },
            minValue = 200,
            maxValue = 2000,
            initValue = user?.cupcapacity ?: 1500
        )
    }

    if (showAssignmentPicker) {
        NumberPickerDialog(
            title = "选择每日目标",
            onDismiss = { showAssignmentPicker = false },
            onValueSelected = { value -> viewModel.updateAssignment(value) },
            minValue = 500,
            maxValue = 5000,
            initValue = user?.assignment ?: 2000
        )
    }

    if (showNicknameDialog) {
        EditDialog(
            title = "请输入新昵称",
            initialValue = user?.nickname ?: "",
            onDismiss = { showNicknameDialog = false },
            onValueChange = { newNickname -> viewModel.updateNickname(newNickname) }
        )
    }
    if (showSignatureDialog) {
        EditDialog(
            title = "请输入新个性签名",
            initialValue = user?.signature ?: "",
            onDismiss = { showSignatureDialog = false },
            onValueChange = { newSignature -> viewModel.updateSignature(newSignature) }
        )
    }

    if (showHistoryDialog) {
        HistoryDialog(
            username = user?.username ?: "",
            onDismiss = { showHistoryDialog = false },
            viewModel = viewModel
        )
    }
}


@Composable
fun EditDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it }
            )
        },
        confirmButton = {
            Button(onClick = {
                onValueChange(text)
                onDismiss()
            }) {
                Text(text = "确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "取消")
            }
        }
    )
}


@Composable
fun NumberPickerDialog(
    title: String,
    onDismiss: () -> Unit,
    onValueSelected: (Int) -> Unit,
    minValue: Int,
    maxValue: Int,
    initValue: Int
) {
    var currentValue by remember { mutableStateOf(initValue) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(title) },
        text = {
            NumberPicker(
                value = currentValue,
                onValueChange = { currentValue = it },
                range = minValue..maxValue
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onValueSelected(currentValue)
                onDismiss()
            }) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("取消")
            }
        }
    )
}


@Composable
fun GenderPickerDialog(onDismiss: () -> Unit, onGenderSelected: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("选择性别") },
        text = {
            Column {
                TextButton(onClick = {
                    onGenderSelected("男")
                    onDismiss()
                }) {
                    Text(text = "男", color = Color.Black)
                }
                TextButton(onClick = {
                    onGenderSelected("女")
                    onDismiss()
                }) {
                    Text(text = "女", color = Color.Black)
                }
            }

        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("取消")
            }
        }
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HistoryDialog(username: String, onDismiss: () -> Unit, viewModel: UserViewModel) {
    val history by viewModel.history.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("饮水记录") },
        text = {
            Column {
                DatePicker(
                    selectedDate = selectedDate,
                    onDateChange = {
                        selectedDate = it
                        viewModel.fetchUserHistory(username, it.toString())
                    }
                )
                history?.let {
                    if (it.isEmpty()) {
                        Text("暂无数据")
                    } else {
                        it.forEach { historyItem ->
                            Text(
                                text = "时间:${historyItem.createdAt?.format(formatter)},饮用量:${historyItem.drink},类型${historyItem.type}"
                            )
                        }
                    }
                }
            }

        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("关闭")
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DatePicker(selectedDate: LocalDate, onDateChange: (LocalDate) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    TextButton(onClick = { showDatePicker = true }) {
        Text(selectedDate.toString())
    }
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.toEpochDay())
        Material3DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDateChange(LocalDate.ofEpochDay(datePickerState.selectedDateMillis!! / (24 * 60 * 60 * 1000)))
                }) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("取消")
                }
            }
        ) {
            Material3DatePicker(
                state = datePickerState
            )
        }
    }
}
