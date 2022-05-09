package com.technorapper.root.ui.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.technorapper.root.data.data_model.lablist.Lab
import com.technorapper.root.ui.components.Alerts
import com.technorapper.root.ui.components.CustomAlertDialog
import com.technorapper.root.ui.components.LabsList

import com.technorapper.root.ui.list.ListActivityViewModel

lateinit var listOfLabs: SnapshotStateList<Lab>

@ExperimentalMaterialApi
@Composable
fun LabsListCompose(
    navController: NavController,
    listActivityViewModel: ListActivityViewModel
) {


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
                    showDialogForSelectedLab(lab)
                }
        }
    }
}
@Composable
@ExperimentalMaterialApi
fun showDialogForSelectedLab(lab: Lab) {
    Alerts.CustomAlertDialog(value = lab) {
        Log.d("meme", it.toString());
    }
}

class LabInfoFromList(lab: List<Lab>) {
    var lab by mutableStateOf(lab)
}