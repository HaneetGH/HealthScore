package com.technorapper.onboarding.ui.onboarding.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technorapper.onboarding.data.repository.onBoardingRepository
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel


@Preview
@Composable
fun LoginCompose(navController: NavController, onBoardingViewModel: OnBoardingViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize(),) {
        val column = createRef()


        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
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
            modifier = Modifier.padding(top=10.dp)
        )
        Button(onClick = { sendPhoneToFirebase() },
            modifier = Modifier.padding(top=10.dp)) {
            Text(
                text = "Generate OTP", style = MaterialTheme.typography.subtitle2, color = Color.White, fontSize = 16.sp
            )

        }
        Button(onClick = { sendOTPToFirebase() },
            modifier = Modifier.padding(top=5.dp)) {
            Text(
                text = "Verify OTP", style = MaterialTheme.typography.subtitle2, color = Color.White, fontSize = 16.sp
            )
        }
    }
}

