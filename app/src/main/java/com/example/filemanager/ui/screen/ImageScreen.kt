package com.example.filemanager.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filemanager.R
import com.example.filemanager.ui.domain.model.ImageModel
import com.example.filemanager.ui.viewmodel.StorageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImageScreen(
    navController: NavHostController, storageViewModel: StorageViewModel, context: Context
) {
    val imageData = storageViewModel.imageData.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("KT File's") }) }) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            modifier = Modifier
                .padding(
                    top = 60.dp, start = 16.dp, end = 16.dp, bottom = 16.dp
                )
                .fillMaxWidth(),
        ) {
            imageData.value.forEach { section ->
                items(items = section.documentList) {
                    PhotoItem(it)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = TextStyle(fontSize = 20.sp),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}


@Composable
fun PhotoItem(photo: ImageModel) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        model = ImageRequest.Builder(LocalContext.current).data(photo.uri).crossfade(true).build(),
        contentDescription = "",
        modifier = Modifier
            .height(120.dp)
            .width(100.dp)
            .padding(4.dp),
        contentScale = ContentScale.Crop
    )
}
