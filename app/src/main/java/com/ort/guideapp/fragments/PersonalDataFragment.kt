package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.entities.Guide
import com.ort.guideapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalDataFragment : Fragment() {
    companion object {
        fun newInstance() = PersonalDataFragment()
    }

    private lateinit var viewModel: PersonalDataViewModel
    lateinit var v : View
    lateinit var guideRepository: GuideRepository

    lateinit var guideNombre: EditText
    lateinit var guideApellido: EditText
    lateinit var guideTelefono: EditText
    lateinit var guidePass: EditText
    lateinit var guidePassNew: EditText
    lateinit var guidePassNewConfirm: EditText
    lateinit var guideDisplayPhoto: EditText
    lateinit var buttonGuardarCambios: Button
    lateinit var buttonCambiarPass: Button
    lateinit var guideId: String
    lateinit var guide: Guide

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_personal_data, container, false)
        guideId = Firebase.auth.currentUser!!.uid
        guideRepository = GuideRepository()
        guideNombre= v.findViewById(R.id.userPersonName_change)
        guideApellido= v.findViewById(R.id.userPersonApellido_change)
        guideTelefono= v.findViewById(R.id.userPersonTelefono_change)
        guideDisplayPhoto = v.findViewById(R.id.userPersonDisplayPhoto_change)
        guidePass = v.findViewById(R.id.userPassRegister_change)
        guidePassNew = v.findViewById(R.id.userPassNewRegister_change)
        guidePassNewConfirm = v.findViewById(R.id.userPassNewConfirmRegister_change)
        buttonGuardarCambios = v.findViewById(R.id.btnGuardarCambios)
        buttonCambiarPass = v.findViewById(R.id.btnCambiarPass)
        return v
    }

    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            guide = guideRepository.getGuideData(guideId)
            guideNombre.setText(guide.name)
            guideApellido.setText(guide.lastname)
            guideTelefono.setText(guide.telefono)
            guideDisplayPhoto.setText(guide.displayPhoto)
        }

        buttonGuardarCambios.setOnClickListener{
            if(checkChanges(guideNombre.text.toString(), guideApellido.text.toString(),
                    guideTelefono.text.toString(), guideDisplayPhoto.text.toString())){

                guideRepository.updateGuide(guideNombre.text.toString(), guideApellido.text.toString(), guideTelefono.text.toString(),
                    guideDisplayPhoto.text.toString(), guide)
                Snackbar.make(v, "Se guardaron los cambios", Snackbar.LENGTH_SHORT).show()
            }
        }

        buttonCambiarPass.setOnClickListener {
            if (checkChangesPass(guidePass.text.toString(), guidePassNew.text.toString(), guidePassNewConfirm.text.toString())){
                guideRepository.updatePassword(guidePassNew.text.toString(), guide)
                Snackbar.make(v, "Se cambió la contraseña", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun checkChanges(nombre: String, apellido: String, telefono: String, foto: String): Boolean {
        if (nombre.isNullOrEmpty()) {
            Snackbar.make(v, "El nombre no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (apellido.isNullOrEmpty()) {
            Snackbar.make(v, "El apellido no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (telefono.isNullOrEmpty()) {
            Snackbar.make(v, "El telefono no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (foto.isNullOrEmpty()) {
            Snackbar.make(v, "La foto no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true //retorna true si se cumplen todos los requisitos
    }

    private fun checkChangesPass(guidePass: String, guidePassNew: String, guidePassNewConfirm: String): Boolean {
        if (guidePass.isNullOrEmpty()) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(guidePassNew.isNullOrEmpty()) {
            Snackbar.make(v, "El password nuevo no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(guidePassNewConfirm.isNullOrEmpty()) {
            Snackbar.make(v, "El password nuevo no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(!guidePassNew.equals(guidePassNewConfirm)) {
            Snackbar.make(v, "Error: Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true //retorna true si se cumplen todos los requisitos
    }
}