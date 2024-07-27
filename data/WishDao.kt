package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWish(wishEntity:Wish)

    //Loads all wishes from wish table
    @Query("SELECT * FROM `wish-table`")
    fun getAllWishes() : Flow<List<Wish>>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateWish(wishEntity: Wish)

    @Delete
    suspend fun deleteWish(wishEntity: Wish)

    //Loads a single wish by id
    @Query("SELECT * FROM `wish-table` WHERE id=:id")
    fun getAWishByID(id:Long) : Flow<Wish>
}