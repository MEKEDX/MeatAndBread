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

    fun getHawawshi(): List<Meal>
    fun getMeat(): List<Meal>
    fun getProcessedMeat(): List<Meal>
    fun getTotalPrice(): Observable<Int>

}
