package com.technorapper.root.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.technorapper.root.data.data_model.lablist.Lab
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun LabsList(
    loading: Boolean,
    Labs: List<Lab>,
    onClick: (Lab) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && Labs.isEmpty()) {
            HorizontalDottedProgressBar()
        } else if (Labs.isEmpty()) {
            NothingHere()
        } else {
            LazyColumn {
                itemsIndexed(
                    items = Labs
                ) { _, labItem ->
                    RecipeCard(
                        lab = labItem,
                        onClick = { onClick.invoke(labItem)


                        }
                    )
                }
            }
        }
    }
}







