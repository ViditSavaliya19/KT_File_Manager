package com.example.filemanager.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
    var imageData = rememberSaveable {
        mutableStateOf<List<FolderModel>?>(null)
    }
    storageViewModel.imageData.observeForever {
        it?.let {
            imageData.value = it
        }
    }


    Scaffold(topBar = { TopAppBar(title = { Text("KT File's") }) }) {
        LazyColumn {
            imageData.value?.let {
                items(it.size) { index ->
                    Text(imageData.value!![index].folderName)
                    LazyColumn {
                        imageData.value!![index].documentList.let {
                            items(it.size) { it1 ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(imageData.value!![index].documentList[it1].uri)
                                        .placeholder(R.drawable.ic_launcher_background).build(),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}