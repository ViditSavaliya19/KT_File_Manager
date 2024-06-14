package com.example.filemanager.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.CaseMap.Fold
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.filemanager.R
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel
import com.example.filemanager.ui.viewmodel.StorageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController, storageViewModel: StorageViewModel, context: Context
) {

    val imageData = storageViewModel.imageData.collectAsState()



    Scaffold(topBar = { TopAppBar(title = { Text("KT File's") }) }) {


//        LazyColumn(modifier = Modifier.padding(top = 60.dp, start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            imageData.value.forEach { section ->
//                item {
//                    SectionHeader(title = section.folderName)
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//                items(section.documentList.chunked(3)) { rowItems ->
//                    Row(modifier = Modifier.fillMaxWidth(),) {
//                        rowItems.forEach { item ->
//                            PhotoItem(item)
//                        }
//                        if (rowItems.size < 2) {
//                            Spacer(modifier = Modifier.weight(1f))
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            content = {
                items(items = imageData.value)
                { (folder, photos) ->
                    FolderItem(folder, photos)
                }
            }
        )

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
fun FolderItem(folder: FolderModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = folder.folderName, style = MaterialTheme.typography.bodyLarge)
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            folder.documentList.map { photo ->
                PhotoItem(photo)
            }
        }
    }
}

@Composable
fun PhotoItem(photo: ImageModel) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo.uri)
            .build(), contentDescription = "", modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(4.dp),
        contentScale = ContentScale.Crop
    )

}

@Composable
fun FolderItem(folder: String, photos: List<ImageModel>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = folder, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(photos) { photo ->
                    PhotoItem(photo = photo)
                }
            }
        )
    }
}