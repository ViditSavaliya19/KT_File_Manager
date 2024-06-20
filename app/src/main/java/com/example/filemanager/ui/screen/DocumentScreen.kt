package com.example.filemanager.ui.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filemanager.R
import com.example.filemanager.ui.domain.model.DataModel
import com.example.filemanager.ui.viewmodel.StorageViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DocumentScreen(
    navController: NavHostController, storageViewModel: StorageViewModel, context: Context
) {

    val dataMap = storageViewModel.imageList.observeAsState()

    var tabIndex = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = {
        dataMap.value?.size ?: 0
    })
    var coroutineScope = rememberCoroutineScope()



    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Audio") },
            actions = {
                IconButton(onClick = { /*TODO*/ }, content = {
                    Icon(
                        Icons.Outlined.Search, contentDescription = "search"
                    )
                })
                IconButton(onClick = { /*TODO*/ }, content = {
                    Icon(
                        Icons.Outlined.GridView, contentDescription = "grid"
                    )
                })
                IconButton(onClick = { /*TODO*/ }, content = {
                    Icon(
                        Icons.Outlined.MoreVert, contentDescription = "more"
                    )
                })
            },
        )
    }) {
        Column(
            modifier = Modifier.padding(it), horizontalAlignment = Alignment.Start
        ) {
            PrimaryScrollableTabRow(selectedTabIndex = pagerState.currentPage, divider = { }) {


                dataMap.value!!.onEachIndexed { index, title ->
                    Tab(
                        text = { Text("${index}") },
                        selected = tabIndex.value == index,
                        onClick = {
                            tabIndex.value = index
                            coroutineScope.launch { pagerState.scrollToPage(index) }
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) {
//                DisplayDocument(dataList = dataMap)
            }

        }

    }

}


@Composable
fun DisplayDocument(modifier: Modifier = Modifier, dataList: List<DataModel>) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier
            .padding(
                top = 60.dp, start = 16.dp, end = 16.dp, bottom = 16.dp
            )
            .fillMaxWidth(),
    ) {
        items(items = dataList) {
            AsyncImage(
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                model = ImageRequest.Builder(LocalContext.current).data(it.filePath).crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .width(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

        }
    }

}