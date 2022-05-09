package com.technorapper.root.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.technorapper.root.data.data_model.lablist.Lab
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
                LabsList(true,uiStateLabList!!,navController)
        }
    }
}
class LabInfoFromList(lab: List<Lab>) {
    var lab by mutableStateOf(lab)
}