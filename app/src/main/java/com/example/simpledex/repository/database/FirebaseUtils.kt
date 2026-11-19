package com.example.simpledex.repository.database

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUtils {
    companion object{
        fun insertPokemon(p: PokemonModel, db: FirebaseFirestore): Boolean{
            val pokemap = hashMapOf(
                "id" to p.id.toString(),
                "name" to p.name,
                "spriteUrl" to p.spriteUrl,
                "hp" to p.hp,
                "atk" to p.atk,
                "def" to p.def,
                "spatk" to p.spatk,
                "spdef" to p.spdef,
                "spd" to p.spd
            )

            var success = false
            db.collection("pokemons").document(p.id.toString()).set(pokemap)
                .addOnSuccessListener {
                    success = true
                }.addOnFailureListener {
                    success = false
                }

            return success
        }

        fun removePokemon(id: String, db: FirebaseFirestore): Boolean{
            var success = false

            db.collection("pokemons").document(id).delete().addOnSuccessListener {
                success = true
            }.addOnFailureListener {
                success = false
            }

            return success
        }

        fun getAllPokemon(db: FirebaseFirestore): List<PokemonModel>{
            var list = listOf<PokemonModel>()
            db.collection("pokemons").get().addOnSuccessListener {
                list = it.toObjects(PokemonModel::class.java)
            }

            return list
        }
    }
}