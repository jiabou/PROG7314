package com.example.fitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitme.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var foodIntakeDao: FoodIntakeDao
    private lateinit var adapter: FoodSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater,container,false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodIntakeDao = FitMeDatabase.getDatabase(requireContext()).foodIntakeDao()

        adapter = FoodSearchAdapter()
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResults.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearchFood.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(requireContext(), "Enter food name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val results = withContext(Dispatchers.IO) {
                    // Example search (case-insensitive)
                    foodIntakeDao.searchFood("%${query}%")
                }

                if (results.isNotEmpty()) {
                    adapter.submitList(results)
                } else {
                    Toast.makeText(requireContext(), "Food not in the database", Toast.LENGTH_SHORT).show()
                    adapter.submitList(emptyList())
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}