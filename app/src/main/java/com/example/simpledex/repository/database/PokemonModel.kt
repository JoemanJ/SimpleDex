package com.example.simpledex.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
class PokemonModel {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "spriteUrl")
    var spriteUrl: String = ""

    @ColumnInfo(name = "name")
    var name = ""

    @ColumnInfo(name = "hp")
    var hp: String = ""

    @ColumnInfo(name = "atk")
    var atk: String = ""

    @ColumnInfo(name = "def")
    var def: String = ""

    @ColumnInfo(name = "spatk")
    var spatk: String = ""

    @ColumnInfo(name = "spdef")
    var spdef: String = ""

    @ColumnInfo(name = "spd")
    var spd: String = ""
}