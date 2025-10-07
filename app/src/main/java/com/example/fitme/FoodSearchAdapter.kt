package com.example.fitme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitme.databinding.ItemFoodSearchBinding

class FoodSearchAdapter : RecyclerView.Adapter<FoodSearchAdapter.ViewHolder>() {
    private var foodList: List<FoodIntake> = emptyList()

    fun submitList(list: List<FoodIntake>) {
        foodList = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemFoodSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = foodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.binding.tvFoodName.text = food.foodName
        holder.binding.tvCalories.text = "${food.calories} kcal"
    }
}