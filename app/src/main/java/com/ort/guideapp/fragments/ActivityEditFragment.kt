package com.ort.guideapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ort.guideapp.R
import com.ort.guideapp.entities.GuideRepository

class ActivityEditFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityEditFragment()
    }

    private lateinit var viewModel: ActivityEditViewModel
    lateinit var guideRepository: GuideRepository
    lateinit var activityTitle: EditText
    lateinit var activityCity: EditText
    lateinit var activityProvince: EditText
    lateinit var activityCountry: EditText
    lateinit var activityDescription: EditText
    lateinit var activityLat: EditText
    lateinit var activityLong: EditText
    lateinit var btnActividadGuardar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityTitle = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityCity)
        activityCity = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityCity)
        activityProvince = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityProvince)
        activityCountry = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityCountry)
        activityDescription = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityDescripcion)
        activityLat = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityLat)
        activityLong = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.activityLong)
        btnActividadGuardar = inflater.inflate(R.layout.fragment_activity_edit, container, false).findViewById(R.id.btnActividadGuardar)
        return inflater.inflate(R.layout.fragment_activity_edit, container, false)
    }

    override fun onStart() {
        super.onStart()

        //TODO logica para crear una actividad de la lista de actividades y de la lista de actividades de un guia
        //y para modificar una actividad en la lista de actividades
        btnActividadGuardar.setOnClickListener(){
            // deberia cuando termine enviarte a actividad ya creada/modificada
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}