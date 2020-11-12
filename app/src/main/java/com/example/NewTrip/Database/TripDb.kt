package com.example.NewTrip.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TripEntity::class],version = 1,exportSchema = false)
abstract class TripDb :RoomDatabase(){

    abstract  fun tripDao(): TripDAo


    companion object{

        @Volatile
        private var INSTANCE:TripDb? = null

        fun getDatabase(context: Context): TripDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDb::class.java,
                    "trips_database"
                ).build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}