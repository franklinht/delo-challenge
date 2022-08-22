package com.frank.delochallenge.model.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostalCode::class], version = 2)
abstract class PostalCodeDatabase : RoomDatabase() {

    abstract fun postalCodeDAO(): PostalCodeDAO

    companion object {

        @Volatile
        private var instance: PostalCodeDatabase? = null

        //TODO: I Could use HILT but i had no time for it
        fun getDatabase(context: Context): PostalCodeDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): PostalCodeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PostalCodeDatabase::class.java,
                "postal_code"
            ).build()
        }
    }
}