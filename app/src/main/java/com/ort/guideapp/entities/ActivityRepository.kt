package com.ort.guideapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.guideapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class ActivityRepository() {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var actividadesCollection = database.collection("actividades")
    private var activityList : MutableList<Activity> = mutableListOf()

    suspend fun getAllActivities(): MutableList<Activity> {
        try{
            val data = actividadesCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await()
            for(document in data){
                activityList.add(document.toObject(Activity::class.java))
            }
        } catch (e: Exception){
            Log.d("Actividades no cargadas: ", activityList.size.toString())
        }
        return activityList
    }

    suspend fun getActivity(uid: String): Activity {
        var activity = Activity()
        try{
            val data = actividadesCollection.document(uid)
                .get().await().toObject(Activity::class.java)!!
            if (data != null) {
                activity = data
            }
        } catch (e: Exception){
            Log.d("Actividad no cargada", "actividad no cargada")
        }
        return activity
    }

    suspend fun deleteActivity(uid: String): Boolean {
        try{
            actividadesCollection.document(uid).delete()
        } catch (e: Exception){
            Log.d("Actividad no cargada", "actividad no cargada")
        }
        return true
    }

    suspend fun addActivity(guideUid: String, activity: Activity): String {
        //crea documento vacio para obtener id
        val newActivity = actividadesCollection.document()
        //asocia id a atributo uid
        activity.uid = newActivity.id
        //se carga actividad con datos
        newActivity.set(activity)
        //retorna activity id para guardar en guia
        return newActivity.id
    }

    suspend fun updateActivity(activityUid: String, titulo: String, descripcion: String){
        actividadesCollection.document(activityUid)
            .update(mapOf("title" to titulo, "description" to descripcion))
    }

}