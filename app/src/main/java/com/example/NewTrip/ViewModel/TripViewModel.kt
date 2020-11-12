package com.example.NewTrip.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.NewTrip.Database.TripDb
import com.example.NewTrip.Database.TripEntity
import com.example.NewTrip.Database.TripRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel (application:Application): AndroidViewModel(application){
    val  getTrips: LiveData<List<TripEntity>>
    private val  repo: TripRepo

   init {
       val tripDAo = TripDb.getDatabase(application).tripDao()

       repo = TripRepo(tripDAo)
       getTrips = repo.getTrips
    }

    fun addTrip(trip:TripEntity){
        viewModelScope.launch(Dispatchers.IO) {

            repo.addTrip(trip)
        }

    }


    fun updateTrip(trip:TripEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            repo.updateTrip(trip)
        }
    }


    fun deleteTrip(trip: TripEntity){
        viewModelScope.launch(Dispatchers.IO){

            repo.deleteTrip(trip)
        }
    }
}