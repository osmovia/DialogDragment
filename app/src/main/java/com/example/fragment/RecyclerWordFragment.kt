package com.example.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.FragmentWordRecyclerBinding
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.appevents.internal.AppEventUtility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import android.view.View.OnAttachStateChangeListener

import android.R.string.no
import android.R.string.no
import android.R.string.no










class RecyclerWordFragment : Fragment(R.layout.fragment_word_recycler) {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SLAVIK", "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d("SLAVIK", "RecyclerWordFragment: onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d("SLAVIK", "RecyclerWordFragment: onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("SLAVIK", "RecyclerWordFragment: onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("SLAVIK", "RecyclerWordFragment: onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("SLAVIK", "RecyclerWordFragment: onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("SLAVIK", "RecyclerWordFragment: onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d("SLAVIK", "RecyclerWordFragment: onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("SLAVIK", "RecyclerWordFragment: onDestroy")
        super.onDestroy()
    }


    private var listCardData: MutableList<CardData> = mutableListOf()
    private var matchedWord: MutableList<CardData> = mutableListOf()
    private var positionClickAndSwipe: Int? = null
    private val dataBase = Firebase.firestore
    private val adapterRecycler = CustomRecyclerAdapter(listCardData, this)
    private lateinit var binding: FragmentWordRecyclerBinding
    private val documentReference = FirebaseFirestore.getInstance().document("collection/document")
    private val firebaseStore = FirebaseFirestore.getInstance().collection("Word")

    private var isSearchActive = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordRecyclerBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        Log.d("SLAVIK", "RecyclerWordFragment: onViewCreated")
        Log.d("SLAVIK", "New list: $listCardData")
        //loadData()

        listFirebase()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapterRecycler



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search(p0)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                search(p0)
                return true
            }
        })


        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            DeleteWordFragment.deleteWordKey
        )?.observe(
            viewLifecycleOwner
        ) {

            if (it == DeleteWordFragment.yes && !isSearchActive) {
                val getCardDataDelete = listCardData[positionClickAndSwipe!!]
                firebaseStore.document(getCardDataDelete.id)
                    .delete()
                listCardData.removeAt(positionClickAndSwipe!!)
                adapterRecycler.notifyItemRemoved(positionClickAndSwipe!!)
                //saveData()
                //sortList()
            }
            if (it == DeleteWordFragment.yes && isSearchActive) {
                val getCardDataDelete = matchedWord[positionClickAndSwipe!!]
                run loop@{ listCardData.forEachIndexed { index, it ->
                        if (getCardDataDelete.id == it.id) {
                            listCardData.removeAt(index)
                            firebaseStore.document(it.id)
                                .delete()
                            matchedWord.removeAt(positionClickAndSwipe!!)
                            adapterRecycler.notifyItemRemoved(positionClickAndSwipe!!)
                            Log.d("SLAVIK", "KEK")
                            return@loop
                        }
                    }
                }
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CardData>(
            DialogAddWord.newWordKey
        )?.observe(
            viewLifecycleOwner
        ) { newWord ->
            listCardData.add(newWord)
            if(isSearchActive) {
                matchedWord.add(newWord)
                binding.searchView.setQuery("", false)
                activity?.hideKeyboard()
            }
            adapterRecycler.notifyDataSetChanged()
            //saveData()
            //sortList()
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CardData>(
            DialogAddWord.changeWordKey
        )?.observe(
            viewLifecycleOwner
        ) { changeWord ->
            adapterRecycler.setWord(changeWord)
            //saveData()
            //sortList()
        }

        val item = object : SwipeToDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                positionClickAndSwipe = viewHolder.absoluteAdapterPosition
                adapterRecycler.notifyItemChanged(positionClickAndSwipe!!)
                findNavController().navigate(R.id.deleteWordFragment)
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.buttonFloatingAction.setOnClickListener{
            findNavController().navigate(R.id.action_recyclerWordFragment_to_dialogAddWord)
        }
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_recyclerWordFragment_to_dialogLogout)
        return true
    }

    fun onItemClick(cardData: CardData) {
        findNavController().navigate(
            R.id.action_recyclerWordFragment_to_dialogAddWord,
            bundleOf(DialogAddWord.cardDataKey to cardData)
        )
    }

    private fun saveData() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "shared preferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(listCardData)
        editor.putString("task list", json)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "shared preferences",
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPreferences.getString("task list", "")
        val type = object : TypeToken<MutableList<CardData>>() {}.type

        listCardData = if (json == null || json.isBlank())
            mutableListOf()
        else
            gson.fromJson(json, type)
    }

    fun addAdaLovelace() {
        val user = hashMapOf("first" to "Viaheslav", "last" to "Osmolivskyi", "born" to 1995)
        dataBase.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("SLAVIK", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { error ->
                Log.d("SLAVIK", "Error adding document", error)
            }
    }

    fun addAlanMatchison() {
        val user =
            hashMapOf("first" to "Igor", "middle" to "Goro", "last" to "Doter", "born" to 2000)
        dataBase.collection("users")
            .add(user)
            .addOnSuccessListener { docunentReference ->
                Log.d("SLAVIK", "DocumentShapshot added with ID: ${docunentReference.id}")
            }
            .addOnFailureListener { error ->
                Log.d("SLAVIK", "Error adding document", error)

            }
    }

    fun getAllUsers() {
        dataBase.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("SLAVIK", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("SLAVIK", "Error getting documents.", exception)
            }
    }

    fun saveWordFirestore() {
        if (listCardData.size == 0) {
            return
        }
        listCardData.forEach { cardData ->
            val id = cardData.id
            val word = cardData.word
            val translate = cardData.translate
        }
        val hashMap = hashMapOf("KEY" to listCardData)
        documentReference.set(hashMap).addOnSuccessListener {
            Log.d("SLAVIK", "Save list")
        }
            .addOnFailureListener {
                Log.d("SLAVIK", "Error save")
            }
    }

    fun loadWordFirestore() {
        documentReference.get().addOnSuccessListener { result ->
            Log.d("SLAVIK", "Result : ${result.data}")
        }
    }

    fun listFirebase() {
        firebaseStore
            .get()
            .addOnSuccessListener { rezult ->
                for (document in rezult) {
                    val id = document.data.get("id")
                    val word = document.data.get("word")
                    val translate = document.data.get("translate")
                    listCardData.add(CardData(word = word.toString(),translate = translate.toString(), id = id.toString()))
                    listCardData.sortBy { it.word }
                    Log.d("SLAVIK", "ListCarData: $listCardData")
                }
                listCardData.sortedBy { it.word }
                adapterRecycler.notifyDataSetChanged()
            }
    }
    fun testFacebook() {
        FacebookSdk.sdkInitialize(context)
        AppEventsLogger.activateApp(context)
    }
    private fun search(text: String?) {
        matchedWord.clear()

        text?.let {
            listCardData.forEach {
                if(it.word.contains(text, true) || it.translate.contains(text, true)) {
                    matchedWord.add(it)
                }
            }
            isSearchActive = true
            updateRecyclerViewSearchWord()
            if(it.isEmpty()) {
                Toast.makeText(requireContext(), "No match found!", Toast.LENGTH_LONG).show()
                isSearchActive = false
                adapterRecycler.mutableList = listCardData
            }
        }
    }
    private fun updateRecyclerViewSearchWord() {
        binding.recyclerView.apply {

            adapterRecycler.mutableList = matchedWord
            adapterRecycler.notifyDataSetChanged()
        }
    }
}

