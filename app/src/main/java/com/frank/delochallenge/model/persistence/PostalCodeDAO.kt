package com.frank.delochallenge.model.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostalCodeDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPostalCodes(postalCode: PostalCode) : Long

    @Transaction
    @Query("SELECT id, count(id) FROM postal_code GROUP BY id")
    fun getCount() : Int

    @Transaction
    @Query("SELECT * FROM postal_code")
    fun getAllPostalCodes() : List<PostalCode>

    //Testing purpose only
    @Query("DELETE FROM postal_code")
    suspend fun deletePostalCodes()
}