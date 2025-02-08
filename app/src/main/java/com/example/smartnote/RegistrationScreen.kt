package com.example.smartnote

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartnote.data.user.UserRepository
import com.example.smartnote.data.viewmodel.RegistrationViewModel
import com.example.smartnote.viewmodelfactory.RegistrationViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    userRepository: UserRepository, // Передаем UserRepository в composable
    viewModel: RegistrationViewModel = viewModel(factory = RegistrationViewModelFactory(userRepository))

) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Регистрация",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Поле имени пользователя
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Имя пользователя") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Электронная почта") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Поле пароля
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) {
                        painterResource(id = R.drawable.eye) // Используйте ваш ресурс для открытого глаза
                    } else {
                        painterResource(id = R.drawable.closed_eye) // Используйте ваш ресурс для закрытого глаза
                    }
                    Image(painter = icon, contentDescription = if (passwordVisible) "Скрыть" else "Показать", modifier = Modifier.clickable { passwordVisible = !passwordVisible })
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка регистрации
        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank() && email.isNotBlank()) {
                    viewModel.registerUser(username, email, password)
                    onBackClick()
                    Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // черный фон
                contentColor = Color.White // белый текст
            )
        ) {
            Text("Зарегистрироваться")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка назад
        Text(
            text = "Назад",
            color = Color.Blue,
            modifier = Modifier.clickable { onBackClick() }
        )
    }
}
