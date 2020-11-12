package com.example.NewTrip.Screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.NewTrip.Database.TripEntity
import com.example.NewTrip.R
import com.example.NewTrip.ViewModel.TripViewModel
import kotlinx.android.synthetic.main.fragment_add_trip.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class AddTrip : Fragment(R.layout.fragment_add_trip) {

    private val args by navArgs<AddTripArgs>()

    private lateinit var tripsViewModel: TripViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (args.TripArgs != null) {
            depature_edtext.text = args.TripArgs?.depature?.toEditable()
            destination_date_edtext.text = args.TripArgs?.depature_date?.toEditable()
            depature_edTime.text = args.TripArgs?.depature_time?.toEditable()
            destination_edtext.text = args.TripArgs?.destination?.toEditable()
            destination_date_edtext.text = args.TripArgs?.destination_date?.toEditable()
            destination_time_edtext.text = args.TripArgs?.destination_time?.toEditable()
            trip_type.text = ((args.TripArgs?.trip_type ?: "Trip Type") as Editable?)

            button_add.text = "UPDATE TRIP"
        }

        tripsViewModel = ViewModelProvider(this).get(TripViewModel::class.java)



        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        trip_type_selector.setOnClickListener {
            showMenu(menu_drop)
        }


        button_add.setOnClickListener {
            val button = it as Button
            if (button.text.toString() == "UPDATE TRIP") {
                addTrip()
            } else {

                addTrip()
            }
        }

        depature_edtext.setOnFocusChangeListener { v, hasFocus ->
            setTitleCase(v, hasFocus)
        }

        destination_edtext.setOnFocusChangeListener { v, hasFocus ->
            setTitleCase(v, hasFocus)
        }

        depature_date_edtext.setOnClickListener {
            selectDate(it)
        }

        destination_date_edtext.setOnClickListener {
            selectDate(it)
        }

        depature_edTime.setOnClickListener {
            selectTime(it)
        }

        destination_time_edtext.setOnClickListener {
            selectTime(it)
        }



    }


    private fun selectDate(v: View) {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, dayOfMonth ->
                val formattedDate = formatDate(mYear, mMonth, dayOfMonth)

                (v as EditText).text = formattedDate.toEditable()
            },
            year,
            month,
            day
        ).show()
    }
    private fun selectTime(v: View) {
        val c = Calendar.getInstance()

        val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        val minOfDay = c.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hour, min ->
                val am_pm = if (hour >= 12) "PM" else "AM"
                val time =
                    if (hour == 12 || hour == 0) "12 : $min $am_pm" else "${hour % 12} : $min $am_pm"
                (v as EditText).text = time.toEditable()
            },
            hourOfDay,
            minOfDay,
            false
        ).show()
    }
    private fun addTrip() {
        val dest = destination_edtext.text.toString()
        val destDate = destination_date_edtext.text.toString()
        val destTime = destination_time_edtext.text.toString()
        val dep = depature_edtext.text.toString()
        val depDate = depature_date_edtext.text.toString()
        val depTime = depature_edTime.text.toString()
        val tripType = trip_type.text.toString()

        val isValid: Boolean =
            dest.isNotEmpty() && destDate.isNotEmpty() && destTime.isNotEmpty() && dep.isNotEmpty() && depDate.isNotEmpty() && depTime.isNotEmpty()
        if (isValid) {

            if (args.TripArgs != null) {

                val trip = TripEntity(
                    args.TripArgs!!.id,
                    dep,
                    depDate,
                    depTime,
                    dest,
                    destDate,
                    destTime,
                    tripType
                )
                tripsViewModel.updateTrip(trip)
                Toast.makeText(context, "Trip Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_listTrip_to_addTrip)

            } else {

                val trip = TripEntity(0, dep, depDate, depTime, dest, destDate, destTime, tripType)
                tripsViewModel.addTrip(trip)
                Toast.makeText(context, "Trip Added Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addTrip_pop_including_listTrip2)

            }

        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showMenu(view: View) {

        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.add_trip_m)
        popupMenu.gravity = Gravity.CENTER

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.business -> {
                    trip_type.text = it.title
                }
                R.id.education -> {
                    trip_type.text = it.title
                }
                R.id.medical -> {
                    trip_type.text = it.title
                }
                R.id.vacation -> {
                    trip_type.text = it.title
                }
            }
            true
        }
    }


    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun formatDate(year: Int, month: Int, day: Int): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDate.of(year, month, day)
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
            val answer: String = date.format(formatter)
            Log.d("answer", answer)
            answer
        } else {

            var date = Date(year - 1900, month, day)
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            val answer: String = formatter.format(date)
            Log.d("answer", answer)
            Log.d("answerYear", year.toString())
            answer

        }
    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.toLowerCase().capitalize() }


    private fun setTitleCase(v: View, hasFocus: Boolean) {
        val editText = v as EditText
        if (!hasFocus) {
            editText.text =  editText.text.toString().capitalizeWords().toEditable()
        }
    }

}