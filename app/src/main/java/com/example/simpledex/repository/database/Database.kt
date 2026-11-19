package com.example.simpledex.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [PokemonModel::class], version = 1, exportSchema = false)
//abstract class MyDatabase(): RoomDatabase() {
//    companion object{
//        private lateinit var INSTANCE: MyDatabase
//        fun getDatabase(context: Context): MyDatabase {
//
//            if(!::INSTANCE.isInitialized) {
//
//                synchronized(Database::class) {
//
//                    INSTANCE = Room.databaseBuilder(context, MyDatabase::class.java, "mydatabase.db")
//                        .allowMainThreadQueries()
//                        .build()
//                }
//            }
//            return INSTANCE
//        }
//    }
//
//    abstract fun PokemonDao(): PokemonDao
//}