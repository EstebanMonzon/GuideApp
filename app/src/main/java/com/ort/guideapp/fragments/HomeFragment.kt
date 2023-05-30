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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.adapters.ActivityAdapter
import com.ort.guideapp.entities.Activity
import com.ort.guideapp.entities.ActivityRepository
import com.ort.guideapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var v: View

    lateinit var recyclerActivity: RecyclerView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    lateinit var guideRepository: GuideRepository
    lateinit var txtBienvenidaNombre: TextView
    lateinit var btnAgregarActividad: Button
    lateinit var imgAvatar: ImageView
    private val user = Firebase.auth.currentUser
    private val userId = user!!.uid
    private lateinit var activityList : MutableList<Activity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        txtBienvenidaNombre = v.findViewById(R.id.txt_Bienvenida)
        recyclerActivity = v.findViewById(R.id.recActivity_home)
        btnAgregarActividad = v.findViewById(R.id.btnAgregarActividad)
        imgAvatar = v.findViewById(R.id.imgAvatarHome)
        activityRepository = ActivityRepository()
        guideRepository = GuideRepository()
        activityList = mutableListOf()
        return v
    }

    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            txtBienvenidaNombre.text = "Bienvenido\n${guideRepository.getGuideName(userId)}"
            activityList = guideRepository.getAllActivitiesGuide(userId)
            recyclerActivity.layoutManager = LinearLayoutManager(context)

            adapterActivity = ActivityAdapter(activityList){ position ->
                val action = HomeFragmentDirections.actionHomeFragmentToActivityDetailFragment(
                    activityList[position]
                )
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity

            Glide.with(v)
                .load(getImage("${guideRepository.getUserAvatar(userId)}"))
                .circleCrop()
                .override(200,200)
                .into(imgAvatar)
        }
        //TODO boton que lleve a fragment de ActivityEditFragment
        btnAgregarActividad.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToActivityEditFragment()
            findNavController().navigate(action)
        }
    }

    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable", getActivity()?.getPackageName() ?: "TourismApp")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
