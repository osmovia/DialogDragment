package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val listCardData: MutableList<CardData> = mutableListOf()

    private lateinit var binding: ActivityMainBinding
    private val model: ClassViewModel by viewModels()

    private lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter =
            CustomRecyclerAdapter(listCardData, this)/* { position -> onListItemClick(position) }*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        model.wordsList.observe(this, { list ->
            adapter.setWords(list)
        })

        model.finished.observe(this, { newData ->
            adapter.setWord(newData)
        })

        model.data.observe(this, {
            listCardData.add(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun showDialog(item: android.view.MenuItem) {
        showEditDialog()

    }

    /*private fun onListItemClick(position: Int) {
        Toast.makeText(this, "Position : $position", Toast.LENGTH_SHORT).show()
    }*/

    fun onItemClick(cardData: CardData) {
        showEditDialog(cardData)
    }

    /*private fun createNewListForAdapter() {
        listForAdapter.clear()
        for(item in listCardData) {
            listForAdapter.add("${item.word}     ----->     ${item.translate}")
        }
    }
    fun clickItemList() {
        binding.lisView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext, "Click position: $position!", Toast.LENGTH_SHORT).show()
            model.liveData.value = listCardData[position]

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val newFragment: DialogFragment = DialogAddWord.newInstance()
            newFragment.show(fragmentTransaction, "dialog_2")

        }
    }*/
}
