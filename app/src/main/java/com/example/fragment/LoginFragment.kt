package com.example.fragment

import android.content.Intent
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
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var callbackManager: CallbackManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = Firebase.auth.currentUser
        callbackManager = CallbackManager.Factory.create()
        if (user != null) {
            findNavController().navigate(R.id.action_loginFragment_to_recyclerWordFragment)
            return
        }
        Log.d("SLAVIK", "return")
        binding.loginButtonFacebook.fragment = this
        binding.loginButtonFacebook.setReadPermissions("email", "public_profile")
        binding.loginButtonFacebook.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("SLAVIK", "Facebook: onSuccess $result")
                    handleFacebookAccessToken(result.accessToken)
                    Toast.makeText(requireContext(), "Registration success!", Toast.LENGTH_LONG)
                        .show()
                }
                override fun onCancel() {
                    Log.d("SLAVIK", "Facebook: onCansel")
                    Toast.makeText(
                        requireContext(),
                        "Oops there was an error. Check your internet connection and try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onError(error: FacebookException?) {
                    Log.d("SLAVIK", "Facebook: onError", error)
                    Toast.makeText(
                        requireContext(),
                        "Oops there was an error. Check your internet connection and try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })


        binding.buttonExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Log.d("SLAVIK", "Exit account")
        }
        binding.buttonFirebase.setOnClickListener {
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
                TextUtils.isEmpty(
                    binding.editTextLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireActivity(), "Please enter email.", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(
                    binding.editTextLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireActivity(), "Please enter password.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val email: String = binding.editTextLoginEmail.text.toString().trim { it <= ' ' }
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
    fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("SLAVIK", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(requireContext(), "Registration success!", Toast.LENGTH_LONG)
                        .show()
                    Log.d("SLAVIK", "signInWithCredential:success")
                    val user = Firebase.auth.currentUser
                    updateUI(user)
                    findNavController().navigate(R.id.action_loginFragment_to_recyclerWordFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SLAVIK", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Oops there was an error. Check your internet connection and try again!",
                        Toast.LENGTH_LONG
                    ).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?){
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
