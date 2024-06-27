package com.example.filemanager.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
//    val audioData = storageViewModel.audioList.observeAsState()
//    val videoData = storageViewModel.videoList.observeAsState()
//    val imageDataList = storageViewModel.imageList.observeAsState()
//    val documentDataList = storageViewModel.documentList.observeAsState()

    Scaffold(Modifier.padding(12.dp)) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
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

            Row {
                CategoryItem(
                    title = "Downloads",
                    size = "22 MB",
                    icon = Icons.Outlined.FileDownload,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }

                Spacer(modifier = Modifier.width(10.dp))
                CategoryItem(
                    title = "Documents",
                    size = "1 GB",
                    icon = Icons.Outlined.Description,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                CategoryItem(
                    title = "Audios",
                    size = "22 MB",
                    icon = Icons.Outlined.MusicNote,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }

                Spacer(modifier = Modifier.width(10.dp))
                CategoryItem(
                    title = "Videos",
                    size = "1 GB",
                    icon = Icons.Outlined.Videocam,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                CategoryItem(
                    title = "Images",
                    size = "22 MB",
                    icon = Icons.Outlined.Photo,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }

                Spacer(modifier = Modifier.width(10.dp))
                CategoryItem(
                    title = "Apps",
                    size = "1 GB",
                    icon = Icons.Outlined.Apps,
                    modifier = Modifier.weight(1f)
                ) {
                    navController.navigate("Documents")
                }


            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Collections", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                CategoryItem(
                    title = "Starred",
                    icon = Icons.Outlined.StarOutline,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CategoryItem(
                    title = "Safe folder",
                    icon = Icons.Outlined.Lock,
                    modifier = Modifier.weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Storage devices", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                CategoryItem(
                    title = "Internal storage",
                    icon = Icons.Outlined.PhoneAndroid,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CategoryItem(
                    title = "Other Storage",
                    icon = Icons.AutoMirrored.Outlined.List,
                    modifier = Modifier.weight(1f)
                )

            }

        }


    }

}

@Composable
fun RecentItem(
    modifier: Modifier = Modifier, folder: FolderModel, navController: NavHostController
) {
    Column {
        Box(
            Modifier.clip(RoundedCornerShape(10.dp))
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
        placeholder = painterResource(id = R.drawable.folder),
        model = ImageRequest.Builder(LocalContext.current).data(photo?.uri).error(R.drawable.folder)
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


@Composable
fun CategoryItem(
    title: String,
    size: String? = null,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    click: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .height(60.dp)
            .fillMaxSize(0.5f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = Color.White)
            .clickable {
                click.invoke()
            }, contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleSmall)
                size?.let {
                    Text(
                        it, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                }
            }
        }

    }

}