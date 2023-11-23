package com.example.roomcustomerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcustomerapp.databinding.ListCustomerBinding
import com.example.roomcustomerapp.db.RoomDB
import com.example.roomcustomerapp.db.entities.CustomerEntity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerAdapter(
    private val customers: MutableList<CustomerEntity>,
    val db: RoomDB,
    val nav: (bundle: Bundle) -> Unit
) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
            ListCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val current = customers[position]
        return holder.bind(current)
    }

    inner class CustomerViewHolder(val binding: ListCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(current: CustomerEntity) {
            binding.txtNameValue.text = current.name + " " + current.surName
            binding.txtBirthdayValue.text = current.birthDay
            CoroutineScope(Dispatchers.Main).launch {
                binding.txtCountryValue.text = db.countryDao().getCountryName(current.countryId)
            }
            binding.btnDelete.setOnClickListener {
                deleteAction(db, current)

            }

            binding.btnEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", current.id.toString())
                bundle.putString("name", current.name)
                bundle.putString("lastname", current.surName)
                bundle.putString("date", current.birthDay)
                nav(bundle)
            }

        }

        private fun deleteAction(db: RoomDB, customer: CustomerEntity) {
            CoroutineScope(Dispatchers.Main).launch {
                db.customerDao().delete(customer)
                customers.remove(customer)
                notifyDataSetChanged()
            }
        }


    }
}