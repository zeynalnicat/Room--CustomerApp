package com.example.roomcustomerapp.db.daos
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomcustomerapp.db.entities.CountryEntity



@Dao
interface CountryDao {

    @Query("Select * from countries ")
    suspend fun selectAll(): MutableList<CountryEntity>

    @Insert
    suspend fun insert(country: CountryEntity)

    @Query("Select id from countries where countries.name==:name ")
    suspend fun getId(name: String): Int

    @Query("Select name from countries where countries.id==:id")
    suspend fun getCountryName(id: Int): String
}