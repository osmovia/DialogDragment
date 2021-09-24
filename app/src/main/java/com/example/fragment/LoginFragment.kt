package com.example.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Log.d("SLAVIK", "Exit account")
        }
        binding.buttonFirebase.setOnClickListener{
            findNavController().navigate(R.id.cloudFirestoreFragment)
        }
        binding.buttonDeveloper.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recyclerWordFragment)
        }

        binding.linerLayoutRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.buttonLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.editTextLoginEmail.text.toString().trim{ it <= ' '}) ->{
                    Toast.makeText(requireActivity(), "Please enter email.", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.editTextLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireActivity(), "Please enter password.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val email : String = binding.editTextLoginEmail.text.toString().trim { it <= ' ' }
                    val password: String = binding.editTextLoginPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireActivity(), "You are logged successfully", Toast.LENGTH_LONG).show()
                                findNavController().navigate(R.id.action_loginFragment_to_recyclerWordFragment)

                            } else {
                                Toast.makeText(requireActivity(), task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }
    }
}
