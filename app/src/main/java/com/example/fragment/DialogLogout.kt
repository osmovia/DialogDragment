package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.LogoutDialogBinding
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class DialogLogout : DialogFragment() {
    lateinit var binding: LogoutDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LogoutDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonYes.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            findNavController().navigate(R.id.action_dialogLogout_to_loginFragment)
        }

        binding.buttonNo.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}