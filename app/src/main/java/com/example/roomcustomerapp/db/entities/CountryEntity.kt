package com.example.roomcustomerapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,

    )
