package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ort.guideapp.R
import com.ort.guideapp.entities.ActivityRepository
import com.ort.guideapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActivityDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityDetailFragment()
    }

    private lateinit var viewModel: ActivityDetailViewModel
    lateinit var v: View
    lateinit var guideRepository: GuideRepository
    lateinit var activityRepository: ActivityRepository

    lateinit var textTitle: TextView
    lateinit var textCity: TextView
    lateinit var textDesc: TextView
    lateinit var textRate: TextView
    lateinit var imageActivity: ImageView
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
        imageActivity = v.findViewById(R.id.imageView_flag)
        btnActivityModificar = v.findViewById(R.id.btnActivityModificar)
        btnActivityBorrar = v.findViewById(R.id.btnActivityBorrar)
        guideRepository = GuideRepository()
        activityRepository = ActivityRepository()
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

        Glide.with(this)
            .load(activity.activityPhoto)
            .centerCrop()
            .into(imageActivity)

        //TODO deberia pasar uid de actividad a siguiente fragment
        btnActivityModificar.setOnClickListener(){
            val action = ActivityDetailFragmentDirections.actionActivityDetailFragmentToEditActivityFragment(activity)
            findNavController().navigate(action)
        }

        btnActivityBorrar.setOnClickListener(){
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                if(activityRepository.deleteActivity(activity.uid)){
                    guideRepository.deleteActivityInGuide(activity.uid, activity.guideUid)
                }
                val action = ActivityDetailFragmentDirections.actionActivityDetailFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}