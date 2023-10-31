package com.kh.mo.meatandbread.local.repo.local

import android.content.Context
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants
import com.kh.mo.meatandbread.util.Constants.burger
import com.kh.mo.meatandbread.util.Constants.burgerDetails
import com.kh.mo.meatandbread.util.Constants.hwa1
import com.kh.mo.meatandbread.util.Constants.hwa1Details
import com.kh.mo.meatandbread.util.Constants.hwa2
import com.kh.mo.meatandbread.util.Constants.hwa2Details
import com.kh.mo.meatandbread.util.Constants.hwa3
import com.kh.mo.meatandbread.util.Constants.hwa3Details
import com.kh.mo.meatandbread.util.Constants.hwa4
import com.kh.mo.meatandbread.util.Constants.hwa4Details
import com.kh.mo.meatandbread.util.Constants.hwa5
import com.kh.mo.meatandbread.util.Constants.hwa5Details
import com.kh.mo.meatandbread.util.Constants.hwa6
import com.kh.mo.meatandbread.util.Constants.hwa6Details
import com.kh.mo.meatandbread.util.Constants.kofta
import com.kh.mo.meatandbread.util.Constants.koftaDetails
import com.kh.mo.meatandbread.util.Constants.mealQuantityHwa
import com.kh.mo.meatandbread.util.Constants.mealQuantityHwaUnit
import com.kh.mo.meatandbread.util.Constants.mealQuantityMeat
import com.kh.mo.meatandbread.util.Constants.mealQuantityProMeat
import com.kh.mo.meatandbread.util.Constants.meat1
import com.kh.mo.meatandbread.util.Constants.meat10
import com.kh.mo.meatandbread.util.Constants.meat10Details
import com.kh.mo.meatandbread.util.Constants.meat11
import com.kh.mo.meatandbread.util.Constants.meat11Details
import com.kh.mo.meatandbread.util.Constants.meat1Details
import com.kh.mo.meatandbread.util.Constants.meat2
import com.kh.mo.meatandbread.util.Constants.meat2Details
import com.kh.mo.meatandbread.util.Constants.meat3
import com.kh.mo.meatandbread.util.Constants.meat3Details
import com.kh.mo.meatandbread.util.Constants.meat4
import com.kh.mo.meatandbread.util.Constants.meat4Details
import com.kh.mo.meatandbread.util.Constants.meat5
import com.kh.mo.meatandbread.util.Constants.meat5Details
import com.kh.mo.meatandbread.util.Constants.meat6
import com.kh.mo.meatandbread.util.Constants.meat6Details
import com.kh.mo.meatandbread.util.Constants.meat7
import com.kh.mo.meatandbread.util.Constants.meat7Details
import com.kh.mo.meatandbread.util.Constants.meat8
import com.kh.mo.meatandbread.util.Constants.meat8Details
import com.kh.mo.meatandbread.util.Constants.meat9
import com.kh.mo.meatandbread.util.Constants.meat9Details

class Data constructor(val context: Context?) {


    fun getData(type: String): List<Meal> {
        return when (type) {
            context?.getString(R.string.hawawshi) -> {

                listOfHawawshi
            }
            context?.getString(R.string.meats) -> listOfMeat
            else -> {
                listOfProcessedMeat
            }
        }
    }


    private val listOfHawawshi: List<Meal> = listOf(
        Meal(
            1,
            R.drawable.haw3,
            hwa1,
            1,
            1,
            20,
            20,
            hwa1Details,
            mealQuantityHwa,
            Constants.hwa,
            20,
            mealQuantityHwaUnit
        ),
        Meal(
            2,
            R.drawable.haw7,
            hwa2,
            1,
            1,
            25,
            25,
            hwa2Details,
            mealQuantityHwa, Constants.hwa,
            25,
            mealQuantityHwaUnit
        ),
        Meal(
            3,
            R.drawable.haw2,
            hwa3, 1,
            1,
            30,
            30,
            hwa3Details, mealQuantityHwa,
            Constants.hwa, 25,
            mealQuantityHwaUnit
        ),
        Meal(
            4,
            R.drawable.haw8,
            hwa4, 1,
            1,
            30,
            30,
            hwa4Details, mealQuantityHwa,
            Constants.hwa,
            25,
            mealQuantityHwaUnit
        ),
        Meal(
            5,
            R.drawable.haw1,
            hwa5, 1,
            1,
            30,
            30,
            hwa5Details, mealQuantityHwa,
            Constants.hwa,
            25,
            mealQuantityHwaUnit
        ),
        Meal(
            6,
            R.drawable.haw5,
            hwa6, 1,
            1,
            35,
            35,
            hwa6Details,
            mealQuantityHwa,
            Constants.hwa,
            25,
            mealQuantityHwaUnit
        ),
    )


    private val listOfMeat: List<Meal> = listOf(
        Meal(
            7,
            R.drawable.meat1,
           meat1, 250,
            250,
            78,
            78,
           meat1Details,
           mealQuantityMeat
        ),
        Meal(
            8,
            R.drawable.meat2,
          meat2, 250,
            250,
            78, 78,
          meat2Details,
      mealQuantityMeat
        ),
        Meal(
            9,
            R.drawable.meat3,
          meat3, 250,
            250,
            78, 78,
          meat3Details,
      mealQuantityMeat
        ),
        Meal(
            10,
            R.drawable.meat4,
          meat4, 250,
            250,
            75, 75,
          meat4Details,
            mealQuantityMeat
        ),
        Meal(
            11,
            R.drawable.meat5,
        meat5, 250,
            250,
            75, 75,
         meat5Details,
           mealQuantityMeat
        ),
        Meal(
            12,
            R.drawable.meat6,
         meat6, 250,
            250,
            60, 60,
           meat6Details,
       mealQuantityMeat
        ),
        Meal(
            13,
            R.drawable.meat7,
          meat7, 250,
            250,
            75, 75,
         meat7Details,
         mealQuantityMeat
        ),
        Meal(
            14,
            R.drawable.meat8,
     meat8, 250,
            250,
            75, 75,
           meat8Details,
           mealQuantityMeat
        ),
        Meal(
            15,
            R.drawable.meat9,
            meat9, 250,
            250,
            78, 78,
         meat9Details,
           mealQuantityMeat
        ),
        Meal(
            16,
            R.drawable.meat10,
         meat10, 250,
            250,
            78, 78,
           meat10Details,
         mealQuantityMeat
        ),
        Meal(
            17,
            R.drawable.meat11,
          meat11, 250,
            250,
            60, 60,
       meat11Details,
         mealQuantityMeat
        ),
    )


    private val listOfProcessedMeat: List<Meal> = listOf(
        Meal(
            18,
            R.drawable.kofta,
            kofta, 500,
            500,
            80, 80,
            koftaDetails,
         mealQuantityProMeat, Constants.proMeat
        ),
        Meal(
            19,
            R.drawable.burger,
           burger, 500,
            500,
            85, 85,
            burgerDetails,
          mealQuantityProMeat, Constants.proMeat
        )
    )
}