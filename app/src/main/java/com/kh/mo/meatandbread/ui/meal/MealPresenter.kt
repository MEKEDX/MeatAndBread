package com.kh.mo.meatandbread.ui.meal

import com.kh.mo.meatandbread.local.repo.Repository
import com.kh.mo.meatandbread.model.Meal
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MealPresenter(
    private val repository: Repository,
    private val mealView: MealView
) : MealPresenterView {


    override fun getMeal(id: Int) {
        repository.getMeal(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ mealView.getMeal(it) }, {})
    }

    override fun saveMeal(meal: Meal) {
        repository.insertMeal(meal).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mealView.savedDone()
            }, {})
    }


}