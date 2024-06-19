package com.example.filemanager.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filemanager.ui.viewmodel.StorageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScreen(
    navController: NavHostController,
    storageViewModel: StorageViewModel,
    context: Context
) {
    val tabs = listOf("All", "Audio", "Video")
    var tabIndex = remember { mutableStateOf(0) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Audio") },
            actions = {
                IconButton(
                    onClick = { /*TODO*/ }, content = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "search"
                        )
                    }
                )
                IconButton(
                    onClick = { /*TODO*/ }, content = {
                        Icon(
                            Icons.Outlined.GridView,
                            contentDescription = "grid"
                        )
                    }
                )
                IconButton(
                    onClick = { /*TODO*/ }, content = {
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = "more"
                        )
                    }
                )
            },
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.Start
        ) {
            PrimaryScrollableTabRow(selectedTabIndex = tabIndex.value, divider = { }) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex.value == index,
                        onClick = { tabIndex.value = index },
                    )
                }
            }

            tabs.takeWhile {
                it == tabs[tabIndex.value]
            }.forEach {
                Text(text = it)
            }

        }

    }

}


@Composable
fun DisplayDocument(modifier: Modifier = Modifier) {

    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
        items(count = 10) {
            Box(
                modifier = modifier
                    .height(100.dp)
                    .width(100.dp)
                    .background(color = Color.Gray)
            ) {

            }
        }
    }

}