package com.example.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    // private var listCardData: MutableList<CardData> = mutableListOf()
    // private var positionFromSwipe: Int? = null

    private lateinit var binding: ActivityMainBinding
    //private val model: ClassViewModel by viewModels()

    //lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Realm.init(this)
         /*loadData()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = CustomRecyclerAdapter(listCardData, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val item = object : SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val fragment = DeleteWordFragment()
                positionFromSwipe = viewHolder.absoluteAdapterPosition
                fragment.show(supportFragmentManager, "tag")
            }

        }
         val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        model.dataAddNewWord.observe(this, {
            adapter.setWords(it)
            saveData()
        })

        model.dataChangeNewWord.observe(this, {
            adapter.setWord(it)
            saveData()
        })

        model.dataDeleteWord.observe(this, {
            if(it == DeleteWordFragment.yes) {
                adapter.delete(positionFromSwipe!!)
            } else {
                adapter.update(positionFromSwipe!!)
            }
            saveData()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    fun onItemClick(cardData: CardData) {
        showDialogFragment(cardData)
    }
    fun showDialog(menu: MenuItem) {
        showDialogFragment()
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(listCardData)
        editor.putString("task list", json)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("task list", "")
        val type = object: TypeToken<MutableList<CardData>>() {}.type

        if(json == null || json.isBlank())
            listCardData = mutableListOf()
        else
            listCardData = gson.fromJson(json, type)
    }*/
    }
}