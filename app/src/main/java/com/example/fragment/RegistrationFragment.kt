package com.example.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentRegistrationBinding
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
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
        Log.d("SLAVIK", "Test Log")

        /*binding.loginButtonFacebook.fragment = this
        binding.    loginButtonFacebook.setReadPermissions("email", "public_profile")
        binding.loginButtonFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("SLAVIK", "facebook:onSuccess loginButton :$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("SLAVIK", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("SLAVIK", "facebook:onError", error)
            }
        })*/

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
    /*fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("SLAVIK", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SLAVIK", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SLAVIK", "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }*/
}