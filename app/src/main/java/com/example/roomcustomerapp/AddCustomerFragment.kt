package com.example.roomcustomerapp

import android.R
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.roomcustomerapp.databinding.FragmentAddCustomerBinding
import com.example.roomcustomerapp.db.RoomDB
import com.example.roomcustomerapp.db.entities.CountryEntity
import com.example.roomcustomerapp.db.entities.CustomerEntity
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCustomerFragment : Fragment() {
    private lateinit var binding: FragmentAddCustomerBinding
    private lateinit var db: RoomDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCustomerBinding.inflate(inflater)
        db = RoomDB.accessDB(requireContext())!!
        adaptArguments()
        setCalendarInput()
        setSpinner()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (arguments == null)
                addToDB()
            else {
                updateDB()
                Snackbar.make(requireView(), "Information saved", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun addToDB() {
        val selectedCountry = binding.spinner.selectedItem?.toString()

        CoroutineScope(Dispatchers.Main).launch {
            val countryId = selectedCountry?.let { db.countryDao().getId(it) } ?: 0
            val chck = db.customerDao().insert(
                CustomerEntity(
                    name = binding.edtName.text.toString(),
                    surName = binding.edtSecondName.text.toString(),
                    birthDay = binding.editTextDate.text.toString(),
                    countryId = countryId  //Countries there should be implemented in app inspection
                )
            )
            if (chck != -1L) {
                Snackbar.make(requireView(), "Successfully Added", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun adaptArguments() {
        arguments?.let {
            val name = it.getString("name")
            val lastname = it.getString("lastname")
            val date = it.getString("date")
            binding.edtName.setText(name)
            binding.edtSecondName.setText(lastname)
            binding.editTextDate.setText(date)
        }
    }

    private fun updateDB() {
        val selectedCountry = binding.spinner.selectedItem?.toString()
        CoroutineScope(Dispatchers.Main).launch {
            val countryId = selectedCountry?.let { db.countryDao().getId(it) } ?: 0
            db.customerDao().update(
                CustomerEntity(
                    id = arguments?.getString("id")?.toIntOrNull() ?: 0,
                    name = binding.edtName.text.toString(),
                    surName = binding.edtSecondName.text.toString(),
                    birthDay = binding.editTextDate.text.toString(),
                    countryId = countryId
                )
            )

        }


    }

    private fun setCalendarInput() {
        binding.editTextDate.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    binding.editTextDate.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
    }

    private fun setSpinner() {
        var countries: MutableList<CountryEntity>?
        val countryName: MutableList<String> = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            countries = db?.let {
                it.countryDao().selectAll()
            }
            for (i in countries!!) {
                countryName.add(i.name)
            }
            val spinner = binding.spinner
            val arrayAdapter =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, countryName)
            spinner.adapter = arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        }
    }

}