package com.technorapper.root.ui.components

import android.R
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.technorapper.root.data.data_model.lablist.Lab


class Alerts {
    companion object {
        @Composable
        fun CustomAlertDialog(value: Lab, setShowDialog: (String) -> Unit) {
            val txtFieldError = remember { mutableStateOf("") }
            val txtField = remember { mutableStateOf(value.labName) }
            Dialog(onDismissRequest = { setShowDialog("close") }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = value.labName,
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily.Default,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Icon(
                                    imageVector = Icons.Filled.Cancel,
                                    contentDescription = "",
                                    tint = colorResource(android.R.color.darker_gray),
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp)
                                        .clickable { setShowDialog("close") }
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        BorderStroke(
                                            width = 2.dp,
                                            color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                        ),
                                        shape = RoundedCornerShape(50)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Money,
                                        contentDescription = "",
                                        tint = colorResource(android.R.color.holo_green_light),
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp)
                                    )
                                },
                                placeholder = { Text(text = "Enter value") },
                                value = txtField.value,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    txtField.value = it.take(10)
                                })

                            Spacer(modifier = Modifier.height(20.dp))

                            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                                Button(
                                    onClick = {
                                        if (txtField.value.isEmpty()) {
                                            txtFieldError.value = "Field can not be empty"
                                            return@Button
                                        }
                                        setShowDialog(txtField.value)
                                    },
                                    shape = RoundedCornerShape(50.dp),
                                    modifier = Modifier
                                        .background(Color.Cyan)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(5.dp)
                                        .background(Color.Red)
                                ) {
                                    Text(text = "Done")
                                }
                            }
                        }
                    }
                }
            }
        }


        @Composable
        fun printtext() {

            Text(text = "test", Modifier.background(color = Color.Blue))
        }

        @Preview(showSystemUi = true, showBackground = true)
        @Composable
        fun JetpackCompose() {
            Card {
                var expanded = remember { mutableStateOf(false) }
                Column(Modifier.clickable { expanded.value = !expanded.component1() }) {
                    Image(
                        painter = painterResource(id = R.mipmap.sym_def_app_icon),
                        contentDescription = "sample"
                    )
                    AnimatedVisibility(visible = expanded.component1()) {

                    }
                    Text(
                        text = "Jetpack Compose",
                        style = MaterialTheme.typography.h2,
                    )
                }
            }
        }
    }
}
