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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technorapper.onboarding.data.repository.onBoardingRepository
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel



@Composable
fun LoginCompose(navController: NavController, onBoardingViewModel: OnBoardingViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val column = createRef()


        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            MessageInput(onBoardingViewModel)

        }
    }
}

@Composable
fun MessageInput(onBoardingViewModel: OnBoardingViewModel
) {
    var inputValue by remember { mutableStateOf("") } // 2
    var inputValue2 by remember { mutableStateOf("") } // 2
    fun sendPhoneToFirebase() {

        onBoardingViewModel.initFirebase(inputValue)
    }
    fun sendOTPToFirebase() {

        onBoardingViewModel.initFirebaseForOTP(inputValue2)
    }
    Column {
        TextField(
            // 4

            value = inputValue,
            onValueChange = { inputValue = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),

        )

        TextField(
            // 4
            value = inputValue2,
            onValueChange = { inputValue2 = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        )
        Button(onClick = { sendPhoneToFirebase() }) {

        }
        Button(onClick = { sendOTPToFirebase() }) {

        }
    }
}

