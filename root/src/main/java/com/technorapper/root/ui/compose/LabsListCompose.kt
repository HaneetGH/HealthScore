package com.technorapper.root.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.technorapper.root.data.data_model.lablist.Lab
import com.technorapper.root.ui.components.Alerts
import com.technorapper.root.ui.components.LabsList
import com.technorapper.root.ui.list.ListActivityViewModel

lateinit var dialogForLab: DialogForLab
var isDialogShow = false

@ExperimentalMaterialApi
@Composable
fun LabsListCompose(
    navController: NavController,
    listActivityViewModel: ListActivityViewModel
) {
    dialogForLab = remember { DialogForLab(Lab("", "", "", "", ""), false) }
    if (dialogForLab.isShow) {
        showDialogForSelectedLab(dialogForLab.lab)
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val column = createRef()


        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val uiStateLabList by listActivityViewModel.uiStateLabList.observeAsState()
            if (uiStateLabList != null)
                LabsList(true, uiStateLabList!!) { lab ->
                    dialogForLab.lab = lab
                    dialogForLab.isShow = true
                }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun showDialogForSelectedLab(lab: Lab) {
    Alerts.CustomAlertDialog(value = lab) {
        dialogForLab.isShow = false
    }
}

class DialogForLab(lab: Lab, isShow: Boolean) {
    var lab by mutableStateOf(lab)
    var isShow by mutableStateOf(isShow)
}