package com.example.wishlistapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavHostController,viewModel: WishViewModel){
    Scaffold(
        topBar = {
            AppBarView(title = "WishList") {

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddScreen.route+"/0L")
                          },
                backgroundColor = Color.Black,
                shape = CircleShape,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null,
                    tint= Color.White)
            }
        },
        backgroundColor = colorResource(id = R.color.app_bar_color)
    ) {

        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier= Modifier
            .padding(it)
            .fillMaxSize()){
            items(wishList.value, key = {wish-> wish.id}){wish->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it==DismissValue.DismissedToStart || it==DismissValue.DismissedToEnd){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )

                SwipeToDismiss(state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if(dismissState.dismissDirection
                                ==DismissDirection.EndToStart) Color.Red else Color.Transparent,
                            label=""
                        )

                        Box(
                           modifier = Modifier
                               .background(color)
                               .padding(horizontal = 20.dp)
                               .fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null,
                                tint= Color.White)
                        }
                    },
                    directions = setOf(
                        DismissDirection.StartToEnd,
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = {FractionalThreshold(0.25f)}
                ) {
                    WishItem(wish = wish,onEditClick={
                        val id =wish.id
                        navController.navigate(Screen.AddScreen.route + "/$id")
                    })
                }


            }
        }
    }
}

@Composable
fun WishItem(wish:Wish,onEditClick:()->Unit){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clickable(onClick = {
                onEditClick()
            }),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}
