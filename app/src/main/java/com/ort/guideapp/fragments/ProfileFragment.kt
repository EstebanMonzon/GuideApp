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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    lateinit var v : View
    lateinit var guideRepository: GuideRepository
    private val user = Firebase.auth.currentUser
    private val userId = user!!.uid
    lateinit var textName: TextView
    lateinit var buttonPersonalData : Button
    lateinit var buttonProfilePicture : Button
    lateinit var imgAvatar: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        textName = v.findViewById(R.id.txtName)
        buttonPersonalData = v.findViewById(R.id.btnEditarDatos)
        buttonProfilePicture = v.findViewById(R.id.btnEditarFoto)
        imgAvatar = v.findViewById(R.id.imgAvatar)
        guideRepository = GuideRepository()
        return v
    }

    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            textName.text = "${guideRepository.getGuideName(userId)}"

            Glide.with(v)
                .load(getImage("${guideRepository.getUserAvatar(userId)}"))
                .circleCrop()
                .override(500,500)
                .into(imgAvatar)
        }

        buttonPersonalData.setOnClickListener(){
            val action = ProfileFragmentDirections.actionProfileFragmentToPersonalDataFragment()
            findNavController().navigate(action)
        }

        buttonProfilePicture.setOnClickListener(){
            val action = ProfileFragmentDirections.actionProfileFragmentToAvatarSelectionFragment()
            findNavController().navigate(action)
        }
    }

    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable", getActivity()?.getPackageName() ?: "TourismApp")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}