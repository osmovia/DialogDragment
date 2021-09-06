package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.fragment.databinding.DialogBinding

class DialogAddWord(val boolean: Boolean) : DialogFragment() {
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

        if(boolean) {
            binding.button.setOnClickListener {
                val cardData = CardData(
                    binding.editTextOriginalWord.text.toString(),
                    binding.editTextTranslateWord.text.toString()
                )
                model.data.value = cardData
                dismiss()
            }
        } else {
            var id = ""
            model.liveData.observe(this, {
                id = it.id
                binding.editTextOriginalWord.setText(it.word)
                binding.editTextTranslateWord.setText(it.translate)
            })

            binding.button.setOnClickListener {
                val cardData = CardData(binding.editTextOriginalWord.text.toString(), binding.editTextTranslateWord.text.toString(), id)
                model.finished.value = cardData
                dismiss()
            }
        }
    }

    /*companion object {
        @JvmStatic
        fun newInstance(boolean: Boolean) = DialogAddWord()
    }*/
}