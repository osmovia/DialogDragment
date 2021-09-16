package com.example.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.linerLayoutRegister.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(R.id.fragmentRegistration, RegistrationFragment())
                .commit()
        }
        binding.buttonLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.editTextLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please enter email.", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.editTextLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please enter password.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val email : String = binding.editTextLoginEmail.text.toString().trim { it <= ' ' }
                    val password: String = binding.editTextLoginPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "You are logged successfully", Toast.LENGTH_LONG).show()

                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                activity?.onBackPressed()
                            } else {
                                Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }

                }
            }
        }
    }
}
