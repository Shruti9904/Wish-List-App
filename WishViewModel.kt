package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
) :ViewModel() {
    var wishTitle by  mutableStateOf("")
    var wishDescription by  mutableStateOf("")

    lateinit var getAllWishes:Flow<List<Wish>>

    init{
        viewModelScope.launch {
            getAllWishes=wishRepository.getAllWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch {
            wishRepository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch {
            wishRepository.deleteWish(wish)
        }
    }

    fun getWishByID(id:Long):Flow<Wish> {
        return wishRepository.getWishById(id)
    }
}