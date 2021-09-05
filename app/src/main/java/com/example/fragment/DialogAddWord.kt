package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.fragment.databinding.DialogBinding

class DialogAddWord : DialogFragment() {
     lateinit var binding: DialogBinding
     val model: ClassViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

        binding.button.setOnClickListener {
            val cardData = CardData(binding.editTextOriginalWord.text.toString(), binding.editTextTranslateWord.text.toString())
            model.data.value = cardData
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DialogAddWord()
    }
}