package com.technorapper.root.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
            /*if (uiStateLabList != null)
                printUserChat(uiStateLabList!!)*/
            if (uiStateLabList != null)
                LabsList(true,uiStateLabList!!,navController)
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
fun LabCell(labObj: Lab) { // 1
    Box(
        modifier = Modifier.background(Color.Gray)
    ) {
        Column {
            Row(

                modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {

                /*AsyncImage(

                 load = {
                     loadSvgPainter(
                         user.imageUrl, density = density
                     )
                 },
                 painterFor = { it },
                 contentDescription = "Idea logo",
                 contentScale = ContentScale.FillWidth,
                 modifier = Modifier.width(50.dp)
             )*/

                Column(
                    modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),

                    ) {
                    Text(
                        text = labObj.labName,
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Box(modifier = Modifier.padding(top = 3.dp)) {
                        Text(

                            text = labObj.labContactPerson,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color(0xFFebe8e8),
                            fontSize = 12.sp
                        )
                    }

                }

            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(start = 8.dp, end = 8.dp))
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