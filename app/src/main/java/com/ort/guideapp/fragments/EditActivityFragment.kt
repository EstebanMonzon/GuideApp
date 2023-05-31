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
import com.ort.guideapp.R
import com.ort.guideapp.entities.ActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivityFragment : Fragment() {

    companion object {
        fun newInstance() = EditActivityFragment()
    }

    private lateinit var v: View
    private lateinit var activityTitle: EditText
    private lateinit var activityDescription: EditText
    private lateinit var btnActividadGuardar: Button
    private lateinit var activityRepository: ActivityRepository
    private lateinit var viewModel: EditActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityRepository = ActivityRepository()

        v = inflater.inflate(R.layout.fragment_edit_activity, container, false)
        activityTitle = v.findViewById(R.id.activityTituloEdit)
        activityDescription = v.findViewById(R.id.activityDescripcionEdit)
        btnActividadGuardar = v.findViewById(R.id.btnActividadGuardar)
        return v
    }

    override fun onStart() {
        super.onStart()
        val activity = EditActivityFragmentArgs.fromBundle(requireArguments()).activity

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityTitle.setText(activity.title)
            activityDescription.setText(activity.description)

        }

        btnActividadGuardar.setOnClickListener {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                activityRepository.updateActivity(activity.uid, activityTitle.text.toString(), activityDescription.text.toString())
                Snackbar.make(v, "Actividad modificada!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditActivityViewModel::class.java)
        // TODO: Use the ViewModel
    }

}