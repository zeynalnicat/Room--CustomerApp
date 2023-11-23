package com.example.roomcustomerapp.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomcustomerapp.db.entities.CustomerEntity


@Dao
interface CustomerDao {

    @Query("Select * from customers")
    suspend fun selectAll(): MutableList<CustomerEntity>

    @Insert
    suspend fun insert(customer: CustomerEntity): Long

    @Delete
    suspend fun delete(customer: CustomerEntity)

    @Update
    suspend fun update(customer: CustomerEntity)

    @Query("Select id from customers where name==:customerName AND surName==:customerSurname ")
    suspend fun getId(customerName: String, customerSurname: String): Int
}