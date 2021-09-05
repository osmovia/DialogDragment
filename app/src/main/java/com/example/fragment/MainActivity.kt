package com.example.fragment

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val listCardData: MutableList<CardData> = mutableListOf()
    private val listForAdapter: MutableList<String> = mutableListOf()

    private lateinit var binding: ActivityMainBinding
    private val model: ClassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        model.data.observe(this, {
            listCardData.add(it)
            createNewListForAdapter()
            updateList()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    fun showDialog(item: android.view.MenuItem) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment: DialogFragment = DialogAddWord.newInstance()
        newFragment.show(fragmentTransaction, "dialog")
    }

    private fun updateList() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listForAdapter )
        binding.lisView.adapter = adapter
    }

    private fun createNewListForAdapter() {
        listForAdapter.clear()
        for(item in listCardData) {
            listForAdapter.add("${item.word}     ----->     ${item.translate}")
        }
    }
}