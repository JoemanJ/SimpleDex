package com.example.simpledex.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpledex.repository.pokeapi.PokemonEntity

@Entity(tableName = "pokemon")
class PokemonModel {
    companion object{
        fun fromPokemonEntity(e: PokemonEntity?): PokemonModel{
            return PokemonModel().apply {
                id = e?.id ?: 0

                spriteUrl = e?.sprites?.frontDefault ?: ""
                name = e?.name ?: ""
                hp = e?.hp.toString()
                atk = e?.atk.toString()
                def = e?.def.toString()
                spatk = e?.spatk.toString()
                spdef = e?.spdef.toString()
                spd = e?.spd.toString()
            }
        }
    }
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