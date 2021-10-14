package com.example.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentAddWordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class
DialogAddWord : DialogFragment() {

    companion object {
        const val newWordKey = "NEW_WORD_KEY"
        const val changeWordKey = "CHANGE_WORD_KEY"
        const val cardDataKey = "CARD_DATA_KEY"
    }

    private val db = FirebaseFirestore.getInstance().collection("Word")
    private val cardData: CardData?
        get() = arguments?.getSerializable(cardDataKey) as? CardData?

    lateinit var binding: FragmentAddWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SLAVIK", "DialogAddWord: onCreate")
        //setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(layoutInflater)
        Log.d("SLAVIK", "DialogAddWord: onCreateView")
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("SLAVIK", "DialogAddWord: onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.hideKeyboard()
        Log.d("SLAVIK", "DialogAddWord: onDestroy")
    }

    override fun onStart() {
        super.onStart()
        Log.d("SLAVIK", "DialogAddWord: onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SLAIK", "DialogAddWord: onStop")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)
        Log.d("SLAVIK", "DialogAddWord: onViewCreated")

        binding.editTextOriginalWord.apply {
            if (cardData == null) {
                requestFocus()
                showSoftKeyboard()
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        binding.editTextTranslateWord.requestFocus()
                        return@OnKeyListener true
                    }
                    false
                })
            } else {
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
        }
        binding.editTextTranslateWord.apply {
            if (cardData == null) {
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        val cardData = CardData(
                            binding.editTextOriginalWord.text.toString(),
                            binding.editTextTranslateWord.text.toString()
                        )
                        db.document(cardData.id).set(cardData).addOnSuccessListener {
                            Log.d("SLAVIK", "Add new word ok. Name document: ${cardData.id}")
                        }.addOnFailureListener {
                            Log.d("SLAVIK", "Error add word", it)
                        }

                        findNavController().previousBackStackEntry?.savedStateHandle?.set(newWordKey, cardData)
                        findNavController().popBackStack()
                        return@OnKeyListener true
                    }
                    false
                })
            } else {
                setText(cardData?.translate)
                setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            changeWordKey, cardData?.apply {
                                word = binding.editTextOriginalWord.text.toString()
                                translate = binding.editTextTranslateWord.text.toString()
                            })
                        db.document(cardData!!.id)
                            .update(mapOf(
                                "word" to cardData!!.word,
                                "translate" to cardData!!.translate
                            ))
                        findNavController().popBackStack()
                        return@OnKeyListener true
                    }
                    false
                })
            }
        }
        binding.button.setOnClickListener {
            if (cardData == null) {

                val cardData = CardData(
                    binding.editTextOriginalWord.text.toString(),
                    binding.editTextTranslateWord.text.toString()
                )
                db.document(cardData.id).set(cardData).addOnSuccessListener {
                    Log.d("SLAVIK", "Add new word ok. Name document: ${cardData.id}")
                }.addOnFailureListener {
                    Log.d("SLAVIK", "Error add word", it)
                }
                findNavController().previousBackStackEntry?.savedStateHandle?.set(newWordKey, cardData)
                findNavController().popBackStack()
            } else {

                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    changeWordKey, cardData?.apply {
                        word = binding.editTextOriginalWord.text.toString()
                        translate = binding.editTextTranslateWord.text.toString()
                    })
                db.document(cardData!!.id)
                    .update(mapOf(
                        "word" to cardData!!.word,
                        "translate" to cardData!!.translate
                    ))
                findNavController().popBackStack()
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
}
/*rules_version = '2';
service cloud.firestore {
    match /databases/{database}/documents {
        match /{document=**} {
            allow read, write: if
            request.time < timestamp.date(2021, 10, 22);
        }
    }
}*/

