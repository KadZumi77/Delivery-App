package com.example.fooddelivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Adapters.PopularAdapter
import com.example.fooddelivery.Models.PopularModel
import com.example.fooddelivery.Models.SharedModel
import com.example.fooddelivery.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: PopularAdapter
    private lateinit var list: ArrayList<PopularModel>
    private lateinit var sharedModel: SharedModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        sharedModel = ViewModelProvider(requireActivity()).get(SharedModel::class.java)

        list = ArrayList()
        list.add(PopularModel(R.drawable.pop_menu_burger, "Sandwich", 7, 7, 1))
        list.add(PopularModel(R.drawable.pop_menu_sandwich, "Momo", 5,5, 1))
        list.add(PopularModel(R.drawable.pop_menu_momo, "Burger", 9,9, 1))
        list.add(PopularModel(R.drawable.pop_menu_burger, "Sandwich", 7, 7, 1))
        list.add(PopularModel(R.drawable.pop_menu_sandwich, "Momo", 5,5, 1))
        list.add(PopularModel(R.drawable.pop_menu_momo, "Burger", 9,9, 1))
        list.add(PopularModel(R.drawable.pop_menu_burger, "Sandwich", 7, 7, 1))
        list.add(PopularModel(R.drawable.pop_menu_sandwich, "Momo", 5,5, 1))
        list.add(PopularModel(R.drawable.pop_menu_momo, "Burger", 9,9, 1))

        adapter = PopularAdapter(requireContext(), list)
        adapter.setSharedModel(sharedModel)

        binding.searchMenuRv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchMenuRv.adapter = adapter

        searchMenuFood()
        return binding.root
    }

    private fun searchMenuFood() {

        binding.searchMenuItem.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(input: String?){  //input - то, что вбиваем в поисковике
        val filteredList = if(input.isNullOrEmpty()){
            list
        } else{
            list.filter { item ->
                item.getFoodName().contains(input, ignoreCase = true) //ignoreCase = true можно писать с маленькой буквы
            }
        }

        adapter.updateList(filteredList as ArrayList<PopularModel>)
    }
}