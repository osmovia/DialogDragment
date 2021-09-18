package com.example.fragment

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.FragmentWordRecyclerBinding

class RecyclerWordFragment : Fragment(R.layout.fragment_word_recycler) {

    private var listCardData: MutableList<CardData> = mutableListOf()
    private var positionClickAndSwipe: Int? = null
    lateinit var adapter: CustomRecyclerAdapter
    lateinit var binding: FragmentWordRecyclerBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordRecyclerBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {

        adapter = CustomRecyclerAdapter(listCardData, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(DeleteWordFragment.deleteWordKey)?.observe(
            viewLifecycleOwner) {
            listCardData.removeAt(positionClickAndSwipe!!)
            adapter.notifyDataSetChanged()
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CardData>(DialogAddWord.newWordKey)?.observe(
            viewLifecycleOwner) { newWord ->
            listCardData.add(newWord)
        }
        viewLifecycleOwner
        val changeWord = arguments?.get(DialogAddWord.changeWordKey)
        if (changeWord != null) {
            changeWord as CardData
            adapter.setWord(changeWord)
        }

        val item = object : SwipeToDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                positionClickAndSwipe = viewHolder.absoluteAdapterPosition
                findNavController().navigate(R.id.deleteWordFragment)
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu,
                                     inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.dialogAddWord)
        return true
    }
    fun onItemClick(cardData: CardData){
        findNavController().navigate(R.id.action_recyclerWordFragment_to_dialogAddWord,
        bundleOf(DialogAddWord.cardDataKey to cardData))
    }
}



