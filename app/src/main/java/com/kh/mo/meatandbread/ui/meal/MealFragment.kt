package com.kh.mo.meatandbread.ui.meal


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants
import com.kh.mo.meatandbread.util.closeFragment
import kotlin.properties.Delegates


class MealFragment : Fragment() {

    private lateinit var mealImage: ImageView
    private lateinit var backButton: ImageView
    private lateinit var shareMeal: ImageView
    private lateinit var mealName: TextView
    private lateinit var mealQuantity: TextView
    private lateinit var mealQuantityMinus: ImageView
    private lateinit var mealQuantityValue: TextView
    private lateinit var mealQuantityPlus: ImageView
    private lateinit var mealPrice: TextView
    private lateinit var mealDetails: TextView
    private lateinit var addToBasket: Button
    private lateinit var meal: Meal
    private var rate by Delegates.notNull<Int>()
    private var price by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)
        receiveData()
        attachData()
        putLimitOfQuantity()
    }

    private fun inti(view: View) {
        mealImage = view.findViewById(R.id.meal_image)
        backButton = view.findViewById(R.id.back_from_meal)
        shareMeal = view.findViewById(R.id.share_meal)
        mealName = view.findViewById(R.id.meal_name)
        mealQuantity = view.findViewById(R.id.meal_quantity)
        mealQuantityMinus = view.findViewById(R.id.productQuantityMinus)
        mealQuantityValue = view.findViewById(R.id.productQuantity_EditText)
        mealQuantityPlus = view.findViewById(R.id.productQuantityPlus)
        mealPrice = view.findViewById(R.id.meal_price)
        mealDetails = view.findViewById(R.id.meal_details)
        addToBasket = view.findViewById(R.id.add_to_basket)
    }

    private fun receiveData() {
        meal = MealFragmentArgs.fromBundle(requireArguments()).meal

    }

    private fun attachData() {
        mealImage.setImageResource(meal.image)
        mealName.text = meal.name
        mealQuantity.text = meal.mealQuantity
        mealQuantityValue.text = meal.mealQuantityRate.toString()
        mealPrice.text = meal.price.toString()
        mealDetails.text = meal.mealDetail
        rate = meal.mealQuantityRate
        price = meal.price
        backButton.setOnClickListener { closeFragment() }
        shareMeal.setOnClickListener { shareProduct()}

        addToBasket.setOnClickListener {

        }

    }

    private fun putLimitOfQuantity(){
        val rateLimits = mapOf(
            Constants.meat to Pair(250, 5000),
            Constants.hwa to Pair(1, 12),
            Constants.proMeat to Pair(500, 5000)
        )
        val (minLimit, maxLimit) = rateLimits[meal.typeofMeal] ?: Pair(0, 0)
        mealQuantityMinus.setOnClickListener {
            if (rate != minLimit) {
                rate -= meal.mealQuantityRate
                price -= meal.price
                mealQuantityValue.text = rate.toString()
                mealPrice.text = price.toString()


            }
        }
        mealQuantityPlus.setOnClickListener {
            if (rate < maxLimit) {
                rate += meal.mealQuantityRate
                price += meal.price
                mealQuantityValue.text = rate.toString()
                mealPrice.text = price.toString()
            }
        }
    }

    private fun shareProduct() {
        val intent = Intent(Intent.ACTION_SEND)
        val shareBody =
            getString(
                R.string.shareProduct,
              meal.name,
              meal.price.toDouble()
            )
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(intent)
    }
}