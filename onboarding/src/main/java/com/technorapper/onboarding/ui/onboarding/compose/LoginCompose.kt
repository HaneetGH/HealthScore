package com.technorapper.onboarding.ui.onboarding.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginCompose(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val column = createRef()

        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            MessageInput()

        }
    }
}

@Composable
fun MessageInput(
) {
    var inputValue by remember { mutableStateOf("") } // 2

    fun sendMessage() {

        inputValue = ""
    }

    Column {
        TextField(
            // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            onValueChange = { inputValue = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )

        TextField(
            // 4
            modifier = Modifier.weight(1f),
            value = inputValue,
            onValueChange = { inputValue = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    LoginCompose(rememberNavController())
}