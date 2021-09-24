package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.fragment.databinding.FragmentFirestoreCloudBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import java.util.*
import kotlin.collections.HashMap

class CloudFirestoreFragment : Fragment() {
    companion object {
        const val firstKey = "FIRST"
        const val secondKey = "SECOND"
    }
    private lateinit var binding: FragmentFirestoreCloudBinding
    private val documentReference = FirebaseFirestore.getInstance().collection("collection").document("document")
    private val documentReferenceList = FirebaseFirestore.getInstance().collection("collectionList").document("documentList")
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirestoreCloudBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = mutableListOf("one", "two", "three", "four", "five", "six", "seven")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, list)
        binding.listView.adapter = adapter

        binding.buttonSaveList.setOnClickListener {
            val alovelaceDocumentRef = db.collection("users").document("alovelace")
            val userColleRef = db.collection("users")
        }
        binding.buttonSaveWord.setOnClickListener {
            val first = binding.editTextFirst.text.toString()
            val second = binding.editTextSecond.text.toString()

            if (first.isEmpty() || second.isEmpty()) {
                return@setOnClickListener
            }
            val dataToSave =  hashMapOf<String, String>()
            dataToSave.put(firstKey, first)
            dataToSave.put(secondKey, second)
            documentReference.set(dataToSave).addOnSuccessListener {
                Log.d("SLAVIK", "Document has been saved!")
            }
                .addOnFailureListener {
                    Log.d("SLAVIK", "Document was not saved!", it)
                }

        }
        binding.buttonDownloadWord.setOnClickListener {
            documentReference.get().addOnSuccessListener {
                Log.d("SLAVIK", "Download ok! ${it.data}")
                val getFirst = it.getString(firstKey)
                val getSecond = it.getString(secondKey)
                binding.textViewDodnloadData.setText("\"" + getFirst + "\" -- " + getSecond)

            }
                .addOnFailureListener {
                    Log.d("SLAVIK", "Error download!", it)
                }
        }
    }

    override fun onStart() {
        super.onStart()
        documentReference.addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
            if (value != null) {
                val getFirst = value.getString(firstKey)
                val getSecond = value.getString(secondKey)
                binding.textViewDodnloadData.setText("\"" + getFirst + "\" -- " + getSecond)
                Log.d("SLAVIK", "Snapshot start!")
            } else if (error != null){
                Log.d("SLAVIK", "Got an error", error)
            }
        }
    }
}
