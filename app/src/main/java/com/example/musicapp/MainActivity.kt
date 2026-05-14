package com.example.musicapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicapp.screen.DetailScreen
import com.example.musicapp.screen.HomeScreen
import com.example.musicapp.ui.theme.MusicAppTheme



class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                val navController = rememberNavController()
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home" ,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        //Home Screen
                        composable(route = "home")
                        {
                            HomeScreen(
                                navController = navController,
                                onAlbumClick = { id ->
                                    navController.navigate("albums/$id")
                                }
                            )
                        }
                        //Detail Screen
                        composable(
                            route = "albums/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStack ->
                            val id = backStack.arguments?.getString("id")

                            DetailScreen(
                                Albumid = id,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MusicAppTheme {
        HomeScreen(innerPadding = PaddingValues(10.dp))
        DetailScreen(
            Albumid = "1",
            navController = rememberNavController()
        )
    }
}