package com.example.NewTrip.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDAo{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(trip:TripEntity)

    @Update
    suspend fun updateTrip(trip:TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("SELECT*FROM USER_TRIPS_TABLE ORDER BY id DESC")

    fun getTips():LiveData<List<TripEntity>>


}
