package com.example.musicapp.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.components.AboutAlbum
import com.example.musicapp.components.ArtistChip
import com.example.musicapp.components.Header
import com.example.musicapp.components.TrackItem
import com.example.musicapp.models.Album
import com.example.musicapp.services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DetailScreen(
    Albumid: String?,
    navController: NavController,
    onBackClick: () -> Unit = {}
){
    val BASE_URL = "https://musicapi.pjasoft.com/"
    var album   by remember { mutableStateOf<Album?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError  by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Albumid) {
        isLoading = true
        hasError  = false
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            album = withContext(Dispatchers.IO) { service.getAlbumById(Albumid ?: "") }
            Log.d("DetailScreen", "Album: $album")  // ← agrega esto
        } catch (e: Exception) {
            Log.e("DetailScreen", "Error: ${e.message}")
            hasError = true
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7F6))
    ) {
        album?.let{ currentAlbum ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                //Header
                item { Header(currentAlbum, onBack = {navController.popBackStack()})}
                //About album
                item { AboutAlbum(currentAlbum) }
                //Chip de artista
                item { ArtistChip(currentAlbum.artist) }
                //Tracks
                items(10) { index ->
                    TrackItem(currentAlbum, index + 1)
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
            //Mini player
            MiniPlayer(
                album = currentAlbum,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }

}




