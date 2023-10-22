package com.kh.mo.meatandbread.ui.cart

import com.kh.mo.meatandbread.local.repo.Repository
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CartPresenter  (
    private val repository: Repository,
    private val cartFragmentView: CartFragmentView
) :CartPresenterView {
     private fun updateMeal(meal: Meal) {
        repository.updateMeal(meal).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cartFragmentView.updateDone()}, {})
    }

    override fun getAllMeals() {
      repository.getAllMeals().subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ cartFragmentView.getMeals(it) }, {})
    }

    override fun deleteMeal(meal: Meal) {
       repository.deleteMeal(meal).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({ cartFragmentView.deleteDone() }, {})
    }

    override fun getTotalPrice() {
        repository.getTotalPrice().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cartFragmentView.getTotalPrice(it) }, {})
    }

    override fun clickPlus(
        meal: Meal,
        mealQuantityValue: Int,
        mealPrice: Int,
        type: String,
        equation: (Int, Int) -> Int
    ) {
        updateMeal( putLimitOfQuantity(
            meal, mealQuantityValue, mealPrice, "+"
        ) { n1: Int, n2: Int -> n1 + n2 })
    }

    override fun clickMinus(
        meal: Meal,
        mealQuantityValue: Int,
        mealPrice: Int,
        type: String,
        equation: (Int, Int) -> Int
    ) {
        updateMeal( putLimitOfQuantity(
            meal, mealQuantityValue, mealPrice, "-"
        ) { n1: Int, n2: Int -> n1 - n2 }
        )
    }


    private fun putLimitOfQuantity(
        meal: Meal,
        mealQuantityValue: Int,
        mealPrice: Int,
        type: String,
        equation: (Int, Int) -> Int

    ): Meal {
        val rateLimits = mapOf(
            Constants.meat to Pair(250, 5000),
            Constants.hwa to Pair(1, 12),
            Constants.proMeat to Pair(500, 5000)
        )
        val (minLimit, maxLimit) = rateLimits[meal.typeofMeal] ?: Pair(0, 0)
        var tempR = 0
        var tempP = 0
        if (mealQuantityValue != minLimit && type == "-") {
            tempR = equation(mealQuantityValue, meal.mealQuantityRate)
            tempP = equation(mealPrice, meal.mealPriceRate)
            return meal.copy(
                mealQuantityValue = tempR,
                price = tempP
            )

        }
        if (mealQuantityValue < maxLimit && type == "+") {
            tempR = equation(mealQuantityValue, meal.mealQuantityRate)
            tempP = equation(mealPrice, meal.mealPriceRate)
            return meal.copy(
                mealQuantityValue = tempR,
                price = tempP
            )
        }
        return meal
    }

}