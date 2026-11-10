package com.example.simpledex.repository.pokeapi

import com.google.gson.annotations.SerializedName

data class MultiPokemonSearch(
    @SerializedName("next")
    val next: String = "",

    @SerializedName("results")
    val pokemon: List<PokemonEntity>? = null
)

data class PokemonEntity(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("sprites")
    val sprites: SpritesEntity? = null,

    @SerializedName("stats")
    val stats: List<StatEntity>? = null
) {
    val frontDefaultSprite: String?
        get() {
            if (!sprites?.frontDefault.isNullOrEmpty()) {
                return sprites.frontDefault
            }
            val pokemonId = if (id > 0) id else url?.trimEnd('/')?.substringAfterLast('/')?.toIntOrNull()
            return if (pokemonId != null) {
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
            } else null
        }

    val hp: Int?
        get() = stats?.find { it.stat?.name == "hp" }?.baseStat

    val atk: Int?
        get() = stats?.find { it.stat?.name == "attack" }?.baseStat

    val def: Int?
        get() = stats?.find { it.stat?.name == "defense" }?.baseStat

    val spatk: Int?
        get() = stats?.find { it.stat?.name == "special-attack" }?.baseStat

    val spdef: Int?
        get() = stats?.find { it.stat?.name == "special-defense" }?.baseStat

    val spd: Int?
        get() = stats?.find { it.stat?.name == "speed" }?.baseStat
}

data class SpritesEntity(
    @SerializedName("front_default")
    val frontDefault: String? = null
)

data class StatEntity(
    @SerializedName("base_stat")
    val baseStat: Int = 0,

    @SerializedName("stat")
    val stat: NamedApiResource? = null
)

data class NamedApiResource(
    @SerializedName("name")
    val name: String? = null
)
