package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val listCardData: MutableList<CardData> = mutableListOf()

    private lateinit var binding: ActivityMainBinding
    private val model: ClassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val myAdapter = CustomRecyclerAdapter(listCardData, this)/* { position -> onListItemClick(position) }*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

        model.finished.observe(this,{ newData ->
            listCardData.forEachIndexed { index, cardData ->
                if(newData.id == cardData.id) {
                    listCardData[index] = newData
                    myAdapter.notifyItemChanged(index)
                }
                Log.d("Slavik" , "index: ${cardData.word}  value: ${cardData.translate}  id: ${cardData.id}")
            }

//            for((word, translate, id) in listCardData) {
//                index++
//                if(newData.id == id) {
//                    index--
//                    listCardData[index] = newData
//                    myAdapter.notifyItemChanged(index)
//                }
//                Log.d("Slavik" , "index: $word  value: $translate  id: $id")
//            }

        })

        model.data.observe(this, {
            listCardData.add(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    fun showDialog(item: android.view.MenuItem) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment: DialogFragment = DialogAddWord(true)
        newFragment.show(fragmentTransaction, "dialog")

    }

    /*private fun onListItemClick(position: Int) {
        Toast.makeText(this, "Position : $position", Toast.LENGTH_SHORT).show()
    }*/

    fun onItemClick(position: Int) {
        model.liveData.value = listCardData[position]
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment: DialogFragment = DialogAddWord(false)
        newFragment.show(fragmentTransaction, "dialog")


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