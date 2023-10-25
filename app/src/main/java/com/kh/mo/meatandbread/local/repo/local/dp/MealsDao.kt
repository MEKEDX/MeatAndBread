package com.kh.mo.meatandbread.local.repo.local.dp

import androidx.room.*
import com.kh.mo.meatandbread.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Meal): Completable

    @Update
    fun updateMeal(meal: Meal): Completable

    @Query("SELECT * FROM MEALS_TABLE")
    fun getAllMeals(): Observable<List<Meal>>

    @Delete
    fun deleteMeal(meal: Meal): Completable

    @Query("SELECT * FROM MEALS_TABLE WHERE id LIKE :id")
    fun getPlanedMeal(id: Int): Single<Meal>

    @Query("SELECT COALESCE(SUM(price), 0) FROM MEALS_TABLE")
    fun getTotalPrice(): Observable<Int>

}