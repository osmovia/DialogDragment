package com.example.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.alreadyHaveAccount.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.editTextRegisterEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireActivity(), "Please enter email.", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.editTextRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireActivity(), "Please enter password.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val email : String = binding.editTextRegisterEmail.text.toString().trim { it <= ' ' }
                    val password: String = binding.editTextRegisterPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firabaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(requireActivity(), "You were registered successfully", Toast.LENGTH_LONG).show()
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(requireActivity(), task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }
    }
}