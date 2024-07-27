package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    //Create
    suspend fun addWish(wish: Wish){
         wishDao.addWish(wish)
     }

    //Read
    fun getAllWishes() : Flow<List<Wish>> {
        return wishDao.getAllWishes()
    }

    //Update
    suspend fun updateWish(wish: Wish){
        wishDao.updateWish(wish)
    }

    //Delete
    suspend fun deleteWish(wish: Wish){
        wishDao.deleteWish(wish)
    }

    //Search
    fun getWishById(id:Long) : Flow<Wish>{
        return wishDao.getAWishByID(id)
    }

}