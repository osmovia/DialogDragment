package com.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fragment.databinding.FragmentDeleteWordBinding


class DeleteWordFragment : DialogFragment() {

    lateinit var binding: FragmentDeleteWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteWordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)
        /*binding.buttonYes.setOnClickListener {
            transferData("yes")
        }
        binding.buttonNo.setOnClickListener {
            transferData("no")
        }

    }
    fun transferData(yesOrNo: String) {
        val args = Bundle()
        args.putString(yesOrNo, yesOrNo)*/
    }
}
