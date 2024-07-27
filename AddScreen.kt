package com.example.wishlistapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddScreen(id:Long,viewModel: WishViewModel,navController: NavHostController){

    val title = if(id!=0L){
        "Update Wish"
    }else{
        "Add Wish"
    }

    val snackMessage = remember{
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if(id!=0L){
        val wish=viewModel.getWishByID(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitle=wish.value.title
        viewModel.wishDescription=wish.value.description
    }else{
        viewModel.wishTitle=""
        viewModel.wishDescription=""
    }
    Scaffold(
        topBar = {
            AppBarView(
                title = title, onBackNavClick = {
                    navController.navigateUp()
                })
        },
        scaffoldState = scaffoldState
    ) {padding->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value =viewModel.wishTitle ,
                onValueChange = {
                    viewModel.wishTitle =it
                },
                label = {
                    Text(text = "Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = TextStyle(textDecoration = TextDecoration.None),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    textColor = Color.Black
                )
            )

            OutlinedTextField(value =viewModel.wishDescription ,
                onValueChange = {
                    viewModel.wishDescription=it
                },
                label = {
                    Text(text = "Description")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    textColor = Color.Black
                ))

            Button(onClick = {
                if(viewModel.wishTitle.isNotEmpty() && viewModel.wishDescription.isNotEmpty()){

                    if(id!=0L){
                        //Update the wish
                        viewModel.updateWish(
                            Wish(
                                id=id,
                                title = viewModel.wishTitle.trim(),
                                description = viewModel.wishDescription.trim()
                            )
                        )
                    }else{
                        //Add a wish
                        viewModel.addWish(
                            Wish(
                                title=viewModel.wishTitle.trim(),
                                description = viewModel.wishDescription.trim()
                            )
                        )
                    }
                    scope.launch {
                        navController.navigateUp()
                    }

                }else{
                    snackMessage.value ="Enter fields to create a wish"
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    }
                }

            },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()) {
                Text(text = title, fontSize = 18.sp)
            }
        }
    }


}
