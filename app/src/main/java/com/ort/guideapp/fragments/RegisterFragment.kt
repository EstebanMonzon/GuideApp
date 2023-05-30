package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.guideapp.R
import com.ort.guideapp.entities.GuideRepository
class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var guideRepository: GuideRepository
    private lateinit var viewModel: RegisterViewModel
    lateinit var v: View

    lateinit var guideEmailText: EditText
    lateinit var guidePassText: EditText
    lateinit var guidePassConfirmText: EditText
    lateinit var buttonRegister: Button
    lateinit var guideNombreText: EditText
    lateinit var guideApellidoText: EditText
    lateinit var guidePersonTelefono: EditText
    lateinit var guideCity: EditText
    lateinit var guideImgText : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)
        firebaseAuth = Firebase.auth
        guideRepository = GuideRepository()
        guideEmailText = v.findViewById(R.id.userEmailRegister)
        guidePassText = v.findViewById(R.id.userPassRegister)
        guidePassConfirmText = v.findViewById(R.id.userPassConfirmRegister)
        buttonRegister = v.findViewById(R.id.btnRegisterEnter)
        guideNombreText= v.findViewById(R.id.userPersonName)
        guideApellidoText= v.findViewById(R.id.userPersonApellido)
        guidePersonTelefono= v.findViewById(R.id.userPersonTelefono)
        guideCity= v.findViewById(R.id.userPersonCity)
        //guideImgText= v.findViewById(R.id.userProfilePhoto)
        return v
    }

    override fun onStart() {
        super.onStart()

        buttonRegister.setOnClickListener{
            if(checkAllFields()){
                crearCuenta(guideNombreText.text.toString(), guideApellidoText.text.toString(), guidePersonTelefono.text.toString(),
                    guideEmailText.text.toString(), guidePassText.text.toString(), guideCity.text.toString())
                //TODO agregar una imagen pre-seteada a guideImgText= v.findViewById(R.id.userProfilePhoto), usar glide
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun crearCuenta(nombre: String, apellido: String, telefono: String, email: String, password: String, city: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if(task.isSuccessful) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisteredOkFragment2()
                val guide = Firebase.auth.currentUser
                guide?.let {
                    guideRepository.crearCuenta(it.uid, nombre, apellido, telefono, email, password, city)
                }
                findNavController().navigate(action)
            }
        }
    }

    private fun checkAllFields(): Boolean {
        if (guideNombreText.length() == 0) {
            Snackbar.make(v, "El nombre no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (guideApellidoText.length() == 0) {
            Snackbar.make(v, "El apellido no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (guidePersonTelefono.length() == 0) {
            Snackbar.make(v, "El telefono no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (guideCity.length() == 0) {
            Snackbar.make(v, "La ciudad no debe estar vacia", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (guideEmailText.length() == 0) {
            Snackbar.make(v, "El email no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (guidePassText.length() == 0) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(guidePassConfirmText.length() == 0) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(!guidePassText.text.toString().equals(guidePassConfirmText.text.toString())) {
            Snackbar.make(v, "Error: Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}