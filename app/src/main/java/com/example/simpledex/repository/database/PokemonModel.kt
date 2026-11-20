package com.example.simpledex.repository.database

import com.example.simpledex.repository.pokeapi.PokemonEntity
import com.google.firebase.firestore.DocumentId

data class PokemonModel(
    @DocumentId
    var id: String = "",
    var spriteUrl: String = "",
    var name: String = "",
    var hp: String = "",
    var atk: String = "",
    var def: String = "",
    var spatk: String = "",
    var spdef: String = "",
    var spd: String = "")
{
    companion object{
        fun fromPokemonEntity(e: PokemonEntity?): PokemonModel{
            return PokemonModel().apply {
                id = e?.id.toString()

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
}