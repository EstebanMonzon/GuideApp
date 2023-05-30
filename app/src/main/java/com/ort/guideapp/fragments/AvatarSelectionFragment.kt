package com.ort.guideapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.entities.Guide
import com.ort.guideapp.entities.GuideRepository
import com.ort.guideapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvatarSelectionFragment : Fragment() {

    companion object {
        fun newInstance() = AvatarSelectionFragment()
    }

    lateinit var v : View
    private lateinit var viewModel: AvatarSelectionViewModel
    lateinit var guideRepository: GuideRepository
    private val user = Firebase.auth.currentUser
    private val userId = user!!.uid
    lateinit var guide: Guide
    lateinit var imgAvatar1: ImageView
    lateinit var imgAvatar2: ImageView
    lateinit var imgAvatar3: ImageView
    lateinit var imgAvatar4: ImageView
    lateinit var imgAvatar5: ImageView
    lateinit var imgAvatar6: ImageView
    lateinit var imgAvatar7: ImageView
    lateinit var imgAvatar8: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_avatar_selection, container, false)

        imgAvatar1 = v.findViewById(R.id.imga1)
        imgAvatar2 = v.findViewById(R.id.imga2)
        imgAvatar3 = v.findViewById(R.id.imga3)
        imgAvatar4 = v.findViewById(R.id.imga4)
        imgAvatar5 = v.findViewById(R.id.imga5)
        imgAvatar6 = v.findViewById(R.id.imga6)
        imgAvatar7 = v.findViewById(R.id.imga7)
        imgAvatar8 = v.findViewById(R.id.imga8)

        guideRepository = GuideRepository()

        return v
    }

    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            guide = guideRepository.getGuideData(userId)

            cargarImagen(imgAvatar1, "a1")
            cargarImagen(imgAvatar2, "a2")
            cargarImagen(imgAvatar3, "a3")
            cargarImagen(imgAvatar4, "a4")
            cargarImagen(imgAvatar5, "a5")
            cargarImagen(imgAvatar6, "a6")
            cargarImagen(imgAvatar7, "a7")
            cargarImagen(imgAvatar8, "a8")
        }

        imgAvatar1.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a1", guide)
        }

        imgAvatar2.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a2", guide)
        }

        imgAvatar3.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a3", guide)
        }

        imgAvatar4.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a4", guide)
        }

        imgAvatar5.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a5", guide)
        }

        imgAvatar6.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a6", guide)
        }

        imgAvatar7.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a7", guide)
        }

        imgAvatar8.setOnClickListener(){
            siguienteActividad()
            actualizarAvatarEnBD("a8", guide)
        }
    }

    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable", getActivity()?.getPackageName() ?: "TourismApp")
    }

    fun cargarImagen(imagen: ImageView, avatar: String) {
        Glide.with(v)
            .load(getImage(avatar))
            .override(400,400)
            .into(imagen)
    }

    fun siguienteActividad() {
        val action = AvatarSelectionFragmentDirections.actionAvatarSelectionFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    fun actualizarAvatarEnBD(avatarElegido: String, guide: Guide) {
        guideRepository.updateAvatar(avatarElegido, guide)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AvatarSelectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}