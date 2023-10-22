package com.kh.mo.meatandbread.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal

class CartAdapter (private val context: Context?,
                   private var meals: List<Meal>,
                   private val onClickListenerCart: OnClickListenerCart
)
    : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val meal = meals[position]
        holder.imageCart.setImageResource(meal.image)
        holder.minusCart.setOnClickListener {
            onClickListenerCart.clickMinus(position,meal.mealQuantityValue,meal.price)
        }
        holder.plusCart.setOnClickListener {
            onClickListenerCart.clickPlus(position,meal.mealQuantityValue,meal.price)

        }
        holder.cancel.setOnClickListener {
            onClickListenerCart.cancel(position)

        }
        holder.nameCart.text=meal.name
        holder.quantityCartRate.text=meal.mealQuantityValue.toString()
        holder.quantityCart.text=meal.mealQuantity
        holder.priceCart.text=meal.price.toString()

        holder.itemView.setOnClickListener{
            onClickListenerCart.clickCartMeal(position)
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageCart: ImageView = itemView.findViewById(R.id.image_meal_cart)
        val nameCart: TextView = itemView.findViewById(R.id.meal_name_cart)
        val quantityCartRate: TextView = itemView.findViewById(R.id.productQuantity_EditText_cart)
        val minusCart: ImageView = itemView.findViewById(R.id.productQuantityMinus_cart)
        val quantityCart: TextView = itemView.findViewById(R.id.meal_quantity_cart)
        val plusCart: ImageView = itemView.findViewById(R.id.productQuantityPlus_cart)
        val priceCart: TextView = itemView.findViewById(R.id.meal_price_cart)
        val cancel: ImageView = itemView.findViewById(R.id.cancel)
    }

    fun updateData(newData: List<Meal>) {
        meals = newData
        notifyDataSetChanged()
    }
}
