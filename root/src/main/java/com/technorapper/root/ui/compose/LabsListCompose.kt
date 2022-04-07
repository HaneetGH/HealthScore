package com.technorapper.root.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.technorapper.root.data.data_model.lablist.Lab
import com.technorapper.root.ui.list.ListActivityViewModel

lateinit var listOfLabs: SnapshotStateList<Lab>

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
                printUserChat(uiStateLabList!!)
        }
    }
}

@Composable
fun printUserChat(user: List<Lab>) {
    // val context = LocalContext.current
    listOfLabs = remember {
        mutableStateListOf()
    }
    listOfLabs.clear()
    listOfLabs.addAll(user)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(user) { lab -> LabCell(lab) }
    }
}

@Composable
fun LabCell(messageItem: Lab) { // 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Card(

            shape = cardShapeFor(), // 3
            backgroundColor =
            Color(0XFF2f3e45),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.labName,
                color =
                Color.White

            )
        }

    }
}

@Composable
fun cardShapeFor(): Shape {
    val roundedCorners = RoundedCornerShape(6.dp)
    return roundedCorners.copy()
}

class LabInfoFromList(lab: List<Lab>) {
    var lab by mutableStateOf(lab)
}