package com.ort.guideapp.entities

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class GuideRepository () {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var guiasCollection = database.collection("guias")
    var activityRepository = ActivityRepository()

    suspend fun getGuideName(userId: String): String {
        return guiasCollection.document(userId).get().await().get("name").toString()
    }

    suspend fun getUserAvatar(userId: String): String {
        return guiasCollection.document(userId).get().await().get("profilePhoto").toString()
    }

    suspend fun getGuideData(userId: String): Guide {
        return guiasCollection.document(userId).get().await().toObject(Guide::class.java)!!
    }

    suspend fun getAllActivitiesGuide(guideId: String): MutableList<Activity> {
        var activitiesGuideList: MutableList<Activity> = mutableListOf()

        try{
            var uidsList: MutableList<String>
            val data = database.collection("guias")
                .document(guideId).get().await().get("activitiesOwnedList")
            if (data != null) {
                uidsList = data as MutableList<String>
                for(uid in uidsList){
                    activitiesGuideList.add(activityRepository.getActivity(uid))
                }
            }
        } catch (e: Exception){
            Log.d("Actividades de guia no fueron cargadas: ", "error de carga de actividades")
        }
        return activitiesGuideList
    }

    fun crearCuenta(uid: String, nombre: String, apellido: String, telefono: String, email: String, password: String, city: String) {
        guiasCollection.document(uid!!)
            .set(Guide(uid, nombre, apellido, email, password, telefono, city, "a1", "",7, mutableListOf()))
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${uid}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun updateGuide(nombre: String, apellido: String, telefono: String, foto:String, guide: Guide){
        guiasCollection.document(guide.uid)
            .update(mapOf("name" to nombre, "lastname" to apellido, "telefono" to telefono, "displayPhoto" to foto))
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

    }

    fun updatePassword(userPassNew: String, guide: Guide) {
        Firebase.auth.currentUser!!.updatePassword(userPassNew)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    guiasCollection.document(guide.uid)
                        .update(mapOf("password" to userPassNew))
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
                    Log.d(ContentValues.TAG, "User password updated.")
                }
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
        guiasCollection.document(guide.uid).update("password", userPassNew)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    fun updateAvatar(avatarId: String, guide: Guide) {
        guiasCollection.document(guide.uid)
            .update(mapOf("profilePhoto" to avatarId))
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    fun deleteActivityInGuide(activityUid: String, guideUid: String) {
        guiasCollection.document(guideUid).update("activitiesOwnedList", FieldValue.arrayRemove(activityUid))
    }

    fun addActivityToGuide(guideUid: String, activityUid: String) {
        guiasCollection.document(guideUid).update("activitiesOwnedList", FieldValue.arrayUnion(activityUid))
    }
}
