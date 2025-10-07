package com.example.fitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitme.databinding.FragmentHomeBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var api: FoodApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://your-render-app.onrender.com") // Replace with your Render URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(FoodApiService::class.java)

        fetchFoods()
    }

    private fun fetchFoods() {
        api.getAllFoods().enqueue(object : Callback<FoodListResponse> {
            override fun onResponse(call: Call<FoodListResponse>, response: Response<FoodListResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val foodList = response.body()!!.data
                    displayFoods(foodList)
                } else {
                    binding.tvFood.text = "Failed to load foods"
                }
            }

            override fun onFailure(call: Call<FoodListResponse>, t: Throwable) {
                binding.tvFood.text = "Error: ${t.message}"
            }
        })
    }

    private fun displayFoods(foodList: List<Food>) {
        val sb = StringBuilder()
        foodList.forEach { food ->
            sb.append("Name: ${food.name}\nCalories: ${food.calories}\nUser ID: ${food.userID}\n\n")
        }
        binding.tvFood.text = sb.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}