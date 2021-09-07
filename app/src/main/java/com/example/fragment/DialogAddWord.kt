package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.fragment.databinding.DialogBinding

class DialogAddWord : DialogFragment() {

    companion object {
        val cardDataKey = "CARD_DATA_KEY"
    }

    private val cardData: CardData?
        get() = arguments?.getSerializable(cardDataKey) as? CardData?

    lateinit var binding: DialogBinding
    private val model: ClassViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

        if (cardData == null) {
            binding.button.setOnClickListener {
                val cardData = CardData(
                    binding.editTextOriginalWord.text.toString(),
                    binding.editTextTranslateWord.text.toString()
                )
                model.wordsList.value?.add(cardData)
                dismiss()
            }
        } else {
            binding.editTextOriginalWord.setText(cardData?.word)
            binding.editTextTranslateWord.setText(cardData?.translate)

            binding.button.setOnClickListener {
                model.finished.value = cardData?.apply {
                    word = binding.editTextOriginalWord.text.toString()
                    translate = binding.editTextTranslateWord.text.toString()
                }
                dismiss()
            }
        }
    }

    /*companion object {
        @JvmStatic
        fun newInstance(boolean: Boolean) = DialogAddWord()
    }*/
}

fun FragmentActivity.showEditDialog(
    cardData: CardData? = null
) {
    val args = Bundle()
    val fragment = DialogAddWord()
    args.putSerializable(DialogAddWord.cardDataKey, cardData)
    fragment.arguments = args
    fragment.show(
        supportFragmentManager,
        "AlertDialog"
    )
}
