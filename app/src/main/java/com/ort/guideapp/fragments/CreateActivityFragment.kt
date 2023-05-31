package com.ort.guideapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.entities.Activity
import com.ort.guideapp.entities.ActivityRepository
import com.ort.guideapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateActivityFragment : Fragment() {
    companion object {
        fun newInstance() = CreateActivityFragment()
    }

    private lateinit var viewModel: CreateActivityViewModel
    private lateinit var activityRepository: ActivityRepository
    private lateinit var guideRepository: GuideRepository
    private lateinit var activityTitle: EditText
    private lateinit var activityCity: EditText
    private  lateinit var activityProvince: EditText
    private lateinit var activityCountry: EditText
    private lateinit var activityDescription: EditText
    private lateinit var activityLat: EditText
    private lateinit var activityLong: EditText
    private lateinit var btnActividadCrear: Button
    private val guideUid = Firebase.auth.currentUser!!.uid
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityRepository = ActivityRepository()
        guideRepository = GuideRepository()

        v = inflater.inflate(R.layout.fragment_create_activity, container, false)
        activityTitle = v.findViewById(R.id.activityTitulo)
        activityCity = v.findViewById(R.id.activityCity)
        activityProvince = v.findViewById(R.id.activityProvince)
        activityCountry = v.findViewById(R.id.activityCountry)
        activityDescription = v.findViewById(R.id.activityDescripcion)
        activityLat = v.findViewById(R.id.activityLat)
        activityLong = v.findViewById(R.id.activityLong)
        btnActividadCrear = v.findViewById(R.id.btnActividadCrear)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnActividadCrear.setOnClickListener(){
            val activity = Activity("",activityTitle.text.toString(), activityCity.text.toString(),
                activityProvince.text.toString(), activityCountry.text.toString(), guideUid ,activityDescription.text.toString(),
                "FOTO", 7, activityLat.text.toString().toDouble(), activityLong.text.toString().toDouble(), mutableListOf())
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                val activityUid = activityRepository.addActivity(guideUid, activity)
                if(!activityUid.isNullOrEmpty()){
                    guideRepository.addActivityToGuide(guideUid, activityUid)
                }
                Snackbar.make(v, "Actividad creada!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateActivityViewModel::class.java)
        // TODO: Use the ViewModel
    }
}