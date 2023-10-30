package com.kh.mo.meatandbread.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.ui.home.HomeFragmentDirections
import com.kh.mo.meatandbread.util.convertToArabicFormat
import com.kh.mo.meatandbread.util.makeGone
import com.kh.mo.meatandbread.util.makeVisible
import kotlin.properties.Delegates

class CartFragment : Fragment(), OnClickListenerCart, CartFragmentView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartPresenter: CartPresenter
    private lateinit var cartPresenterView: CartPresenterView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var checkoutValue: TextView
    private lateinit var checkout: Button
    private lateinit var lottieAnimationView: LottieAnimationView
    private var totalTime: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)
        setUp()


    }

    private fun inti(view: View) {
        recyclerView = view.findViewById(R.id.recycle_of_meals_cart)
        checkoutValue = view.findViewById(R.id.checkout_value)
        checkout = view.findViewById(R.id.checkout)
        lottieAnimationView = view.findViewById(R.id.lottie_animation_no_product)

    }

    private fun setUp() {
        checkoutValue.text = "0"
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter
        cartPresenter = CartPresenter(
            RepositoryIm(LocalSource.getInstance(requireContext())),
            this
        )

        checkout.setOnClickListener {
            showWeekDialog()
            navigateToTimer()
        }

        cartPresenterView = cartPresenter
        cartPresenterView.getAllMeals()
        cartPresenterView.getTotalPrice()
        cartPresenterView.getTotalTime()
    }

    override fun clickCartMeal(meal: Meal) {
        findNavController().navigate(
            CartFragmentDirections.actionCartToMealFragment(meal)
        )
    }

    override fun clickPlus(
        meal: Meal,
        mealQuantityValue: Int,
        mealPrice: Int
    ) {
        cartPresenterView.clickPlus(
            meal, mealQuantityValue, mealPrice, "+"
        ) { n1: Int, n2: Int -> n1 + n2 }
    }

    override fun clickMinus(meal: Meal, mealQuantityValue: Int, mealPrice: Int) {
        cartPresenterView.clickMinus(
            meal, mealQuantityValue, mealPrice, "-"
        ) { n1: Int, n2: Int -> n1 - n2 }

    }

    override fun deleteMeal(meal: Meal) {
        cartPresenterView.deleteMeal(meal)

    }

    override fun getMeals(meals: List<Meal>) {
        if (meals.isEmpty()) {
            lottieAnimationView.makeVisible()
        } else {
            lottieAnimationView.makeGone()
        }
        cartAdapter.submitList(meals)
    }


    private fun showWeekDialog() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(resources.getString(R.string.title))
//            .setMessage(resources.getString(R.string.supporting_text))
//            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
//                // Respond to neutral button press
//            }
//            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                // Respond to negative button press
//            }
//            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
//                // Respond to positive button press
//            }
//            .show()

    }


    override fun deleteDone() {}

    override fun updateDone() {
    }

    override fun getTotalPrice(totalPrice: Int) {
        if (totalPrice != 0) {
            checkoutValue.makeVisible()
            checkoutValue.text = totalPrice.convertToArabicFormat()
        } else {
            checkoutValue.makeGone()
        }
    }

    override fun getTotalTime(totalTime: Int) {
        this.totalTime = totalTime
    }

    private fun isCartListEmpty()=recyclerView.isEmpty()




    private fun navigateToTimer() {
        if(!isCartListEmpty()){
            findNavController().navigate(
                CartFragmentDirections.actionCartToWaiting(totalTime)
            )
        }else{
            Toast.makeText(requireActivity(), "اضف للسلة بعض المشتريات", Toast.LENGTH_SHORT).show()}

    }
}