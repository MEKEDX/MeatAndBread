package com.kh.mo.meatandbread.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants

class CartFragment : Fragment(), OnClickListenerCart, CartFragmentView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var meals: ArrayList<Meal>
    private lateinit var cartPresenter: CartPresenter
    private lateinit var cartPresenterView: CartPresenterView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var checkoutValue: TextView

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
    private fun inti(view :View){
        recyclerView = view.findViewById(R.id.recycle_of_meals_cart)
        checkoutValue = view.findViewById(R.id.checkout_value)

    }

    private fun setUp(){
        checkoutValue.text="0"
        meals = arrayListOf()
        cartAdapter = CartAdapter(context, meals, this)
        recyclerView.adapter = cartAdapter
        cartPresenter = CartPresenter(
            RepositoryIm(LocalSource.getInstance(requireContext())),
            this
        )
        cartPresenterView = cartPresenter
        cartPresenterView.getAllMeals()
        cartPresenterView.getTotalPrice()
    }
    override fun clickCartMeal(position: Int) {
        findNavController().navigate(
            CartFragmentDirections.actionCartToMealFragment(meals[position])
        )
    }

    override fun clickPlus(
        position: Int,
        mealQuantityValue: Int,
        mealPrice: Int
    ) {
        cartPresenterView.clickPlus(
            meals[position], mealQuantityValue, mealPrice,"+"
        ) { n1: Int, n2: Int -> n1 + n2 }
    }

    override fun clickMinus(position: Int, mealQuantityValue: Int, mealPrice:Int) {
        cartPresenterView.clickMinus(
            meals[position], mealQuantityValue, mealPrice,"-"
        ) { n1: Int, n2: Int -> n1 - n2 }

    }

    override fun cancel(position: Int) {
        cartPresenterView.deleteMeal(meals[position])
    }

    override fun getMeals(meals: List<Meal>) {
        this.meals = meals as ArrayList<Meal>
        cartAdapter.updateData(meals)
    }




    override fun deleteDone() {
        Toast.makeText(requireActivity(), "تم الحذف", Toast.LENGTH_SHORT).show()
    }

    override fun updateDone() {
    }

    override fun getTotalPrice(totalPrice: Int) {
        checkoutValue.text=totalPrice.toString()
    }

}