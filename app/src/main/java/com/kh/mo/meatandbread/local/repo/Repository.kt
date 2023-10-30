package com.kh.mo.meatandbread.local.repo

import com.kh.mo.meatandbread.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun insertMeal(meal: Meal): Completable
    fun updateMeal(meal: Meal): Completable
    fun getAllMeals(): Observable<List<Meal>>
    fun deleteMeal(meal: Meal): Completable
    fun getMeal(id: Int): Single<Meal>

    fun getMeals(type:String): List<Meal>
    fun getTotalPrice(): Observable<Int>
    fun getTotalTime(): Observable<Int>

}
