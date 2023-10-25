package com.kh.mo.meatandbread.ui.meals

import com.kh.mo.meatandbread.model.Meal

interface MealsView {
fun getMeals(meals:List<Meal>)
}