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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.technorapper.root.ui.list.ListActivityViewModel

lateinit var notesList: SnapshotStateList<String>

@Composable
fun LabsListCompose(navController: NavController, listActivityViewModel: ListActivityViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val column = createRef()


        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            printUserChat(
                listOf("Haneet", "Katto", "Milan", "Deepak"),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun printUserChat(user: List<String>, modifier: Modifier) {
    // val context = LocalContext.current
    notesList = remember {
        mutableStateListOf()
    }
    notesList.clear()
    notesList.addAll(user)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(notesList) { lab -> LabCell(lab) }
    }
}

@Composable
fun LabCell(messageItem: String) { // 1
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor(messageItem), // 3
            backgroundColor =
            Color(0XFF2f3e45),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem,
                color =
                Color.White

            )
        }

    }
}

@Composable
fun cardShapeFor(user: String): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return roundedCorners.copy(bottomStart = CornerSize(0))
}
