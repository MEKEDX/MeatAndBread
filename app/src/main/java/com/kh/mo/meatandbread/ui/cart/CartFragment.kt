package com.kh.mo.meatandbread.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.makeGone
import com.kh.mo.meatandbread.util.makeVisible

class CartFragment : Fragment(), OnClickListenerCart, CartFragmentView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var meals: List<Meal>
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
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        cartPresenter = CartPresenter(
            RepositoryIm(LocalSource.getInstance(requireContext())),
            this
        )
        cartPresenterView = cartPresenter
        cartPresenterView.getAllMeals()
        cartPresenterView.getTotalPrice()
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
            meal, mealQuantityValue, mealPrice,"+"
        ) { n1: Int, n2: Int -> n1 + n2 }
    }

    override fun clickMinus(meal: Meal, mealQuantityValue: Int, mealPrice:Int) {
        cartPresenterView.clickMinus(
           meal, mealQuantityValue, mealPrice,"-"
        ) { n1: Int, n2: Int -> n1 - n2 }

    }

    override fun deleteMeal(meal: Meal) {
        cartPresenterView.deleteMeal(meal)
    }

    override fun getMeals(meals: List<Meal>) {
        Toast.makeText(requireActivity(), "${meals.size}", Toast.LENGTH_SHORT).show()
        this.meals = meals
        cartAdapter.submitList(meals)
    }




    override fun deleteDone() {
        Toast.makeText(requireActivity(), "تم الحذف", Toast.LENGTH_SHORT).show()
    }

    override fun updateDone() {
    }

    override fun getTotalPrice(totalPrice: Int) {
    if(totalPrice!=0){
        checkoutValue.makeVisible()
        checkoutValue.text=totalPrice.toString()
    }else{
        checkoutValue.makeGone()
    }
    }

}