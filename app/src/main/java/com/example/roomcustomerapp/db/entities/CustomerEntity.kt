package com.example.roomcustomerapp.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    "Customers", foreignKeys = [ForeignKey(
        entity = CountryEntity::class,
        parentColumns = ["id"],
        childColumns = ["countryId"]
    )]
)
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surName: String,
    val birthDay: String,
    val countryId: Int
)
