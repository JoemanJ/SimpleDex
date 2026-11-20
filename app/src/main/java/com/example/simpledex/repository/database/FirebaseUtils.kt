package com.example.simpledex.repository.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUtils {
    companion object{
        fun favoritePokemon(p: PokemonModel, db: FirebaseFirestore): Boolean{
            val pokemap = hashMapOf(
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
            db.collection("favoritos")
                .document(auth.currentUser?.uid?:"")
                .collection("pokemons")
                .document(p.id).set(pokemap)
                .addOnSuccessListener {
                    success = true
                }.addOnFailureListener {
                    success = false
                }

            return success
        }

        fun unfavoritePokemon(id: String, db: FirebaseFirestore): Boolean{
            var success = false

            db.collection("favoritos")
                .document(auth.currentUser?.uid ?: "")
                .collection("pokemons")
                .document(id)
                .delete()
                .addOnSuccessListener {
                    success = true
                }.addOnFailureListener {
                    success = false
                }

            return success
        }

        fun getFavoritePokemon(db: FirebaseFirestore, onResult: (List<PokemonModel>) -> Unit) {
            db.collection("favoritos")
                .document(auth.currentUser?.uid ?: "")
                .collection("pokemons")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val list = querySnapshot.toObjects(PokemonModel::class.java)
                    onResult(list)
                }
                .addOnFailureListener {
                    onResult(emptyList())
                }
        }
        private val auth = FirebaseAuth.getInstance()
    }
}