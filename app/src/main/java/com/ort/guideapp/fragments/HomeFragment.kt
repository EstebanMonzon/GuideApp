package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
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
//import com.ort.guideapp.adapters.ActivityAdapter
//import com.ort.guideapp.adapters.GuideAdapter
import com.ort.guideapp.entities.Activity
import com.ort.guideapp.entities.ActivityRepository
import com.ort.guideapp.entities.Guide
import com.ort.guideapp.entities.GuideRepository
import com.ort.guideapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    /*___________________________________ onStart ___________________________________*/
    private lateinit var viewModel: HomeViewModel
    lateinit var v: View

    lateinit var recyclerActivity: RecyclerView
    //lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    lateinit var guideRepository: GuideRepository
    lateinit var userRepository: UserRepository

    lateinit var recyclerGuide: RecyclerView
    //lateinit var adapterGuide: GuideAdapter

    lateinit var searchView: SearchView
    lateinit var txtBienvenidaNombre: TextView
    lateinit var btnActividadesVerTodo: Button
    lateinit var btnGuidesVerTodo: Button

    lateinit var imgAvatar: ImageView

    private val user = Firebase.auth.currentUser
    private val userId = user!!.uid
    private lateinit var activityList : MutableList<Activity>
    private lateinit var guideList : MutableList<Guide>

    /*___________________________________ onCreateView ___________________________________*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = v.findViewById(R.id.searchView_home)
        txtBienvenidaNombre = v.findViewById(R.id.txt_Bienvenida)
        recyclerActivity = v.findViewById(R.id.recActivity_home)
        recyclerGuide = v.findViewById(R.id.recGuide_home)
        btnActividadesVerTodo = v.findViewById(R.id.btnActividadesVerTodo)
        btnGuidesVerTodo = v.findViewById(R.id.btnGuidesVerTodo)
        imgAvatar = v.findViewById(R.id.imgAvatarHome)
        activityRepository = ActivityRepository()
        guideRepository = GuideRepository()
        userRepository = UserRepository()
        activityList = mutableListOf()
        guideList = mutableListOf()
        return v
    }

    /*___________________________________ onStart ___________________________________*/
    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            txtBienvenidaNombre.text = "Bienvenido\n${userRepository.getUserName(userId)}"
            activityList = activityRepository.getHomeActivityList()
            guideList = guideRepository.getHomeGuideList()

            recyclerActivity.layoutManager = LinearLayoutManager(context)
            recyclerGuide.layoutManager = LinearLayoutManager(context)

            /*
            adapterActivity = ActivityAdapter(activityList){ position ->
                val action = HomeFragmentDirections.actionHomeFragmentToActivityDetailFragment(
                    activityList[position]
                )
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity

            adapterGuide = GuideAdapter(guideList){ position ->
                val action = HomeFragmentDirections.actionHomeFragmentToGuideDetailFragment(
                    guideList[position])
                findNavController().navigate(action)
            }
            recyclerGuide.adapter = adapterGuide
            */

            Glide.with(v)
                .load(getImage("${userRepository.getUserAvatar(userId)}"))
                .circleCrop()
                .override(200,200)
                .into(imgAvatar)
        }

        //TODO generar funcion que busque por palabra clave en lista de actividades

        btnActividadesVerTodo.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToActivitiesListFragment()
            findNavController().navigate(action)
        }
    }

    /*___________________________________ getImage ___________________________________*/
    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable", getActivity()?.getPackageName() ?: "TourismApp")
    }

    /*___________________________________ onActivityCreated ___________________________________*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }
    //TODO porque no se ajusta el scroll al segundo recyclerview?
    //TODO HACER METODO DE SALIR DE USUARIO (Ver documentacion de google)
    //TODO usar Storage y Glide para guardar las fotos subidas de cada actividad que cree el guia en su app (PARA APP GUIA)
    //TODO hacer logica del searchBar
}
