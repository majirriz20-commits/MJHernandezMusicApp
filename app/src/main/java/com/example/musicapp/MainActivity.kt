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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicapp.screen.HomeScreen
import com.example.musicapp.ui.theme.MusicAppTheme



class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) {
                    val navController = rememberNavController()
                    // Quitamos el Scaffold de aquí para que no estorbe
                    NavHost(
                        navController = navController,
                        startDestination = "albums"
                    ) {
                        composable(route = "albums") {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = "albums/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStack ->
                            val id = backStack.arguments?.getInt("id") ?: 0
                            /*ProductDetailScreen(
                                id = id,
                                onBackClick = { navController.popBackStack() }
                            )*/
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
    }
}