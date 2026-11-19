package com.example.simpledex.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.simpledex.repository.pokeapi.PokemonEntity

//@Dao
//interface PokemonDao {
//    @Insert
//    fun insert(p: PokemonModel): Long
//
//    @Update
//    fun update(p: PokemonModel): Int
//
//    @Delete
//    fun delete(p: PokemonModel)
//
//    @Query("SELECT * FROM pokemon")
//    fun getSavedPokemon(): List<PokemonModel>
//
//    @Query("SELECT * FROM pokemon WHERE id = :id")
//    fun getPokemonById(id: Int): List<PokemonModel>
//}