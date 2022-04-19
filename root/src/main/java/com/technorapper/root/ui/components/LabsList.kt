package com.technorapper.root.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.technorapper.root.data.data_model.lablist.Lab
import com.technorapper.root.ui.list.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun LabsList(
    loading: Boolean,
    recipes: List<Lab>,
    /*onChangeScrollPosition: (Int) -> Unit,*/
    /*page: Int,
    onTriggerNextPage: () -> Unit,*/
    nav: NavController,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            HorizontalDottedProgressBar()
//            LoadingRecipeListShimmer(imageHeight = 250.dp,)
        } else if (recipes.isEmpty()) {
            NothingHere()
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    RecipeCard(
                        lab = recipe,
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}






