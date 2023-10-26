package com.kh.mo.meatandbread.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.convertToArabicFormat

class CartAdapter(
    private val onClickListenerCart: OnClickListenerCart
) : ListAdapter<Meal, CartAdapter.CartViewHolder>(MealsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val meal = getItem(position)
        holder.imageCart.setImageResource(meal.image)
        holder.minusCart.setOnClickListener {
            onClickListenerCart.clickMinus(meal, meal.mealQuantityValue, meal.price)
        }
        holder.plusCart.setOnClickListener {
            onClickListenerCart.clickPlus(meal, meal.mealQuantityValue, meal.price)

        }
        holder.delete.setOnClickListener {
            onClickListenerCart.deleteMeal(meal)

        }
        holder.nameCart.text = meal.name
        holder.quantityCartRate.text = meal.mealQuantityValue.convertToArabicFormat()
        holder.quantityCart.text = meal.mealQuantity
        holder.priceCart.text = meal.price.convertToArabicFormat()

        holder.itemView.setOnClickListener {
            onClickListenerCart.clickCartMeal(meal)
        }
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageCart: ImageView = itemView.findViewById(R.id.image_meal_cart)
        val nameCart: TextView = itemView.findViewById(R.id.meal_name_cart)
        val quantityCartRate: TextView = itemView.findViewById(R.id.productQuantity_EditText_cart)
        val minusCart: ImageView = itemView.findViewById(R.id.productQuantityMinus_cart)
        val quantityCart: TextView = itemView.findViewById(R.id.meal_quantity_cart)
        val plusCart: ImageView = itemView.findViewById(R.id.productQuantityPlus_cart)
        val priceCart: TextView = itemView.findViewById(R.id.meal_price_cart)
        val delete: ImageView = itemView.findViewById(R.id.cancel)
    }

    class MealsDiffUtil : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.mealQuantityValue == newItem.mealQuantityValue&&
                    oldItem.price == newItem.price
        }

    }

}
