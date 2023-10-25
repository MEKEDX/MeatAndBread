package com.kh.mo.meatandbread.ui.meals

import com.kh.mo.meatandbread.local.repo.Repository

class MealsPresenter(private val repository: Repository,
                     private val mealsView: MealsView):MealsPresenterView {

    override fun getMeals(type: String) {
        mealsView.getMeals(repository.getMeals(type))
        }

}