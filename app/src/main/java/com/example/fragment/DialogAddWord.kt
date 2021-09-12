package com.example.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.fragment.databinding.DialogBinding

class DialogAddWord : DialogFragment() {

    companion object {
        const val cardDataKey = "CARD_DATA_KEY"
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
            binding.editTextOriginalWord.apply {
                requestFocus()
                showSoftKeyboard()
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        binding.editTextTranslateWord.requestFocus()
                        return@OnKeyListener true
                    }
                    false
                })
            }
            binding.editTextTranslateWord.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    val cardData = CardData(
                        binding.editTextOriginalWord.text.toString(),
                        binding.editTextTranslateWord.text.toString()
                    )
                    model.dataAddNewWord.value = cardData
                    dismiss()
                    return@OnKeyListener true
                }
                false
            })

            binding.button.setOnClickListener {
                val cardData = CardData(
                    binding.editTextOriginalWord.text.toString(),
                    binding.editTextTranslateWord.text.toString()
                )
                model.dataAddNewWord.value = cardData
                dismiss()
            }
        } else {
            binding.editTextOriginalWord.apply {
                setText(cardData?.word)
                requestFocus()
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        binding.editTextTranslateWord.requestFocus()
                        return@OnKeyListener true
                    }
                    false
                })
            }
            binding.editTextTranslateWord.apply {
                setText(cardData?.translate)
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        model.dataChangeNewWord.value = cardData?.apply {
                            word = binding.editTextOriginalWord.text.toString()
                            translate = binding.editTextTranslateWord.text.toString()
                        }
                        dismiss()
                        return@OnKeyListener true
                    }
                    false
                })
            }

            binding.button.setOnClickListener {
                model.dataChangeNewWord.value = cardData?.apply {
                    word = binding.editTextOriginalWord.text.toString()
                    translate = binding.editTextTranslateWord.text.toString()
                }
                dismiss()
            }
        }
    }
    private fun EditText.showSoftKeyboard() {
        post {
            val inputMethodManager =
                context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(this, SHOW_IMPLICIT)
        }
    }
    private fun clickEnter(view: View) {
        view.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                binding.editTextTranslateWord.requestFocus()
                return@OnKeyListener true
            }
            false
        })
    }

}
fun FragmentActivity.showDialogFragment(cardData: CardData? = null) {
    val args = Bundle()
    val fragment = DialogAddWord()
    args.putSerializable(DialogAddWord.cardDataKey, cardData)
    fragment.arguments = args
    fragment.show(supportFragmentManager, "dialog")
}
