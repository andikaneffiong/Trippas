package com.example.NewTrip.Database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "user_trips_table")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val  depature : String,
    val depature_date: String,
    val depature_time: String,
    val destination : String,
    val destination_date: String,
    val destination_time: String,
    val trip_type: String,
):Parcelable