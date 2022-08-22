package com.frank.delochallenge.model.persistence

import androidx.room.*

@Dao
interface PostalCodeDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPostalCodes(postalCode: PostalCode) : Long

    @Transaction
    @Query("SELECT id, count(id) FROM postal_code GROUP BY id")
    fun getCount() : Long

    //Testing purpose only
    @Query("DELETE FROM postal_code")
    suspend fun deletePostalCodes()
}