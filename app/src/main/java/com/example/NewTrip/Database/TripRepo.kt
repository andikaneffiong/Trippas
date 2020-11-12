package com.example.NewTrip.Database

import androidx.lifecycle.LiveData

class TripRepo (private val  tripDAo: TripDAo){
    val getTrips: LiveData<List<TripEntity>> = tripDAo.getTips()
    suspend fun addTrip(trip:TripEntity){
        tripDAo.addTrip(trip)
    }
    suspend fun updateTrip(trip:TripEntity){
        tripDAo.updateTrip(trip)

    }
    suspend fun deleteTrip(trip: TripEntity){
        tripDAo.deleteTrip(trip)
    }
}