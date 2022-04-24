package com.technorapper.onboarding.ui.onboarding.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


import androidx.navigation.NavController
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel

lateinit var isOtpVisible: IsOtpVisible

@Preview
@Composable
fun LoginCompose(navController: NavController, onBoardingViewModel: OnBoardingViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val column = createRef()


        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            isOtpVisible = remember { IsOtpVisible(false) }
            showViews(onBoardingViewModel = onBoardingViewModel, isOtpVisible)
        }
    }
}

@Composable
fun showViews(onBoardingViewModel: OnBoardingViewModel, isOtpVisible: IsOtpVisible) {
    if (!isOtpVisible.isOtpVisible)
        PhoneNumView(onBoardingViewModel)
    else
        OtpView(onBoardingViewModel = onBoardingViewModel)
}

@Composable
fun PhoneNumView(
    onBoardingViewModel: OnBoardingViewModel
) {
    var inputValuePhone by remember { mutableStateOf("") } // 2

    fun sendPhoneToFirebase() {

        onBoardingViewModel.initFirebase(inputValuePhone)
    }

    Column {
        TextField(
            value = inputValuePhone,
            onValueChange = { inputValuePhone = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),

            )
        Button(
            onClick = { sendPhoneToFirebase() },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(
                text = "Generate OTP",
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun OtpView(
    onBoardingViewModel: OnBoardingViewModel
) {

    var inputValueOtp by remember { mutableStateOf("") } // 2


    fun sendOTPToFirebase() {

        onBoardingViewModel.initFirebaseForOTP(inputValueOtp)
    }
    Column {


        TextField(
            // 4
            value = inputValueOtp,
            onValueChange = { inputValueOtp = it.toString() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            modifier = Modifier.padding(top = 10.dp)
        )
        Button(
            onClick = { sendOTPToFirebase() },
            modifier = Modifier.padding(top = 5.dp)
        ) {
            Text(
                text = "Verify OTP",
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

class IsOtpVisible(isOtpView: Boolean) {
    var isOtpVisible by mutableStateOf(isOtpView)
}

