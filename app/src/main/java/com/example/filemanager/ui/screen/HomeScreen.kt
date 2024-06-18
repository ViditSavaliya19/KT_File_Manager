package com.example.filemanager.ui.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filemanager.R
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel
import com.example.filemanager.ui.viewmodel.StorageViewModel


@Composable
fun HomeScreen(
    navController: NavHostController, storageViewModel: StorageViewModel, context: Context
) {
    val imageData = storageViewModel.imageData.collectAsState()
    val audioData = storageViewModel.audioList.observeAsState()
    val videoData = storageViewModel.videoList.observeAsState()
    val imageDataList = storageViewModel.imageList.observeAsState()
    val documentDataList = storageViewModel.documentList.observeAsState()

    Scaffold(Modifier.padding(12.dp)) {
        Column(Modifier.padding(it)) {
            Text(text = "Recent", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                items(count = imageData.value.size) {
                    RecentItem(folder = imageData.value[it], navController = navController)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Categories", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                audioData.value?.size?.let { it1 ->
                    items(it1)
                    {
                        Text(text = "${audioData.value!![it].fileName}")
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                documentDataList.value?.size?.let { it1 ->
                    items(it1)
                    {
                        Text(text = "${documentDataList.value!![it].fileName}")
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                imageDataList.value?.size?.let { it1 ->
                    items(it1)
                    {
                        Text(text = "${imageDataList.value!![it].fileName}")
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                videoData.value?.size?.let { it1 ->
                    items(it1)
                    {
                        Text(text = "${videoData.value!![it].fileName}")
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Collections", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Storage devices", style = MaterialTheme.typography.titleMedium)
        }


    }

}

@Composable
fun RecentItem(
    modifier: Modifier = Modifier,
    folder: FolderModel,
    navController: NavHostController
) {
    Column {
        Box(
            Modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            Column {
                Row {
                    ShowImage(folder, 0, navController)
                    ShowImage(folder, 1, navController)
                }
                Row {
                    ShowImage(folder, 2, navController)
                    ShowImage(folder, 3, navController)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = folder.folderName, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Image", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ShowImage(folder: FolderModel, index: Int, navController: NavHostController) {

    if (folder.documentList.size > index) {
        FolderPhotoItem(folder.documentList[index], navController)
    } else {
        FolderPhotoItem(navController = navController)
    }

}

@Composable
fun FolderPhotoItem(photo: ImageModel? = null, navController: NavHostController) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo?.uri)
            .error(R.drawable.ic_launcher_background)
            .crossfade(true).build(),
        contentDescription = "",
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .clickable {
                navController.navigate("Image")
            },
        contentScale = ContentScale.Crop
    )
}