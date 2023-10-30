package com.kh.mo.meatandbread.local.repo

import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RepositoryIm(private val localSource: LocalSource) : Repository {

    override fun insertMeal(meal: Meal): Completable {
        return localSource.mealDao.mealDao().insertMeal(meal)
    }

    override fun updateMeal(meal: Meal): Completable {
        return localSource.mealDao.mealDao().updateMeal(meal)
    }

    override fun getAllMeals(): Observable<List<Meal>> {
        return localSource.mealDao.mealDao().getAllMeals()
    }

    override fun deleteMeal(meal: Meal): Completable {
        return localSource.mealDao.mealDao().deleteMeal(meal)
    }
    override fun getTotalPrice(): Observable<Int> {
        return localSource.mealDao.mealDao().getTotalPrice()
    }

    override fun getTotalTime(): Observable<Int> {
        return localSource.mealDao.mealDao().getTotalTime()

    }

    override fun getMeal(id: Int): Single<Meal> {
        return localSource.mealDao.mealDao().getPlanedMeal(id)
    }

    override fun getMeals(type:String): List<Meal> {
      return  localSource.getMeals(type)
    }






}