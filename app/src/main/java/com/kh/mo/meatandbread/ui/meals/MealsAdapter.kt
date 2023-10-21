package com.kh.mo.meatandbread.ui.meals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal

class MealsAdapter(private val context: Context?,
                   private val meals: List<Meal>,
                   private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.imageOfMeal.setImageResource(meal.image)
        holder.textOfMeal.text = meal.name
        holder.itemView.setOnClickListener{
            onClickListener.clickMeal(position)
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            val imageOfMeal: ImageView = itemView.findViewById(R.id.image_meal)
            val textOfMeal: TextView = itemView.findViewById(R.id.name_meal)
        }



}
