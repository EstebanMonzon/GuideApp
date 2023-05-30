package com.ort.guideapp.entities

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class UserRepository {
    /*val database = FirebaseSingleton.getInstance().getDatabase()
    private var usersCollection = database.collection("usuarios")
    private var favActivityList : MutableList<Activity> = mutableListOf()

    suspend fun getUserName(userId: String): String {
        return usersCollection.document(userId).get().await().get("name").toString()
    }

    suspend fun getUserAvatar(userId: String): String {
        return usersCollection.document(userId).get().await().get("profilePhoto").toString()
    }

    suspend fun getUserData(userId: String): User {
        return usersCollection.document(userId).get().await().toObject(User::class.java)!!
    }

    fun crearCuenta( uid: String, nombre: String, apellido: String, telefono:String, email: String, password: String, ) {
        usersCollection.document(uid!!)
            .set(User(uid, nombre, apellido, telefono, email, password, "a1", mutableListOf())) //TODO falta agregar la foto de perfil aca
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${uid}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

     fun updateUser(nombre: String, apellido: String, telefono: String,user: User){
         usersCollection.document(user.uid)
             .update(mapOf("name" to nombre, "lastname" to apellido, "telefono" to telefono))
             .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
             .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }

    fun updatePassword(userPassNew: String, user: User) {
        Firebase.auth.currentUser!!.updatePassword(userPassNew)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    usersCollection.document(user.uid)
                        .update(mapOf("password" to userPassNew))
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    Log.d(TAG, "User password updated.")
                }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun updateAvatar(avatarId: String, user: User)
    {
        usersCollection.document(user.uid)
            .update(mapOf("profilePhoto" to avatarId))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }*/
}