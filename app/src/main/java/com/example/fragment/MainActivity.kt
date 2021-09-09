package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val listCardData: MutableList<CardData> = mutableListOf()

    private lateinit var binding: ActivityMainBinding
    private val model: ClassViewModel by viewModels()

     lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = CustomRecyclerAdapter(listCardData, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val item = object : SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val fragment = DeleteWordFragment()
                fragment.show(supportFragmentManager, "tag")
            }

        }
         val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        model.data.observe(this, {
            adapter.setWords(it)
        })

        model.finished.observe(this, { newData ->
            adapter.setWord(newData)
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
}