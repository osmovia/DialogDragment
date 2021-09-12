package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.fragment.databinding.FragmentDeleteWordBinding


class DeleteWordFragment : DialogFragment() {
    companion object {
        const val yes = "YES"
        const val no = "NO"
    }

    lateinit var binding: FragmentDeleteWordBinding
    private val model: ClassViewModel by activityViewModels()

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
        binding.buttonYes.setOnClickListener {
            transferData(yes)
        }
        binding.buttonNo.setOnClickListener {
            transferData(no)
        }

    }
    private fun transferData(yesOrNo: String) {
        model.dataDeleteWord.value = yesOrNo
        dismiss()
    }
}
