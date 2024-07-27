package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun myApp(wishViewModel: WishViewModel = viewModel()){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeView(navController,wishViewModel)

        }
        composable(route= Screen.AddScreen.route+"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type= NavType.LongType
                    defaultValue= 0L
                    nullable=false
                }
            )
        ){entry->
            val id =if(entry.arguments!=null) entry.arguments!!.getLong("id") else 0L
            AddScreen(
                id=id,
                viewModel = wishViewModel,
                navController = navController
            )
        }
    }
}