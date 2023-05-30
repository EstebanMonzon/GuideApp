package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ort.guideapp.R
import com.ort.guideapp.entities.GuideRepository


class ActivityDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityDetailFragment()
    }

    private lateinit var viewModel: ActivityDetailViewModel
    lateinit var v: View
    lateinit var guideRepository: GuideRepository

    lateinit var textTitle: TextView
    lateinit var textCity: TextView
    lateinit var textDesc: TextView
    lateinit var textRate: TextView
    lateinit var btnActivityModificar : Button
    lateinit var btnActivityBorrar : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activity_detail, container, false)
        textTitle = v.findViewById(R.id.txtActivityTitle)
        textCity = v.findViewById(R.id.txtCity)
        textDesc = v.findViewById(R.id.txtActivityDesc)
        textRate = v.findViewById(R.id.textRate)
        btnActivityModificar = v.findViewById(R.id.btnActivityModificar)
        btnActivityBorrar = v.findViewById(R.id.btnActivityBorrar)
        guideRepository = GuideRepository()
        return v
    }

    override fun onStart() {
        super.onStart()
        val activity = ActivityDetailFragmentArgs.fromBundle(requireArguments()).activity
        val title = activity.title
        val city = activity.city
        val description = activity.description
        val rate = activity.rate
        textTitle.text = title
        textCity.text = city
        textDesc.text = description
        textRate.text = rate.toString()

        //TODO logica para modificar una actividad de la lista de actividades
        btnActivityModificar.setOnClickListener(){

        }
        //TODO logica para borrar una actividad de la lista de actividades y de la lista de actividades de un guia
        btnActivityBorrar.setOnClickListener(){

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}