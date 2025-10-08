package com.example.fitme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private var foodList: MutableList<AuthResponse>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName: TextView = itemView.findViewById(R.id.tvFoodName)
        val tvCalories: TextView = itemView.findViewById(R.id.tvCalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        //holder.tvFoodName.text = food.foodName
        holder.tvCalories.text = "${food.calories} kcal"
    }

    override fun getItemCount(): Int = foodList.size

    fun updateData(newList: MutableList<AuthResponse>) {
        foodList = newList
        notifyDataSetChanged()
    }
}