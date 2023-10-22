package com.kh.mo.meatandbread.local.repo.local

import android.content.Context
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants

class Data(context: Context?) {
    val listOfHawawshi: List<Meal> = listOf(
        Meal(1,
            R.drawable.haw3, context?.getString(R.string.hwa1).toString(),
            1,
            1,
            20,
            20,

            context?.getString(R.string.hwa1Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,20
        ),
        Meal(2,
            R.drawable.haw7, context?.getString(R.string.hwa2).toString(), 1,1,
            25,25,
            context?.getString(R.string.hwa2Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,25
        ),
        Meal(3,
            R.drawable.haw2, context?.getString(R.string.hwa3).toString(),1,
            1, 30,30,
            context?.getString(R.string.hwa3Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,25
        ),
        Meal(4,
            R.drawable.haw8, context?.getString(R.string.hwa4).toString(), 1,
            1, 30,30,
            context?.getString(R.string.hwa4Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,25
        ),
        Meal(5,
            R.drawable.haw1, context?.getString(R.string.hwa5).toString(),1,
            1, 30,30,
            context?.getString(R.string.hwa5Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,25
        ),
        Meal(6,
            R.drawable.haw5, context?.getString(R.string.hwa6).toString(), 1,
            1, 35,35,
            context?.getString(R.string.hwa6Details).toString(),
            context?.getString(R.string.mealQuantityHwa).toString(), Constants.hwa,25
        ),
    )


    val listOfMeat: List<Meal> = listOf(
        Meal(7,
            R.drawable.meat1,
            context?.getString(R.string.meat1).toString(),250,
            250,
            78,
            78,
            context?.getString(R.string.meat1Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(8,
            R.drawable.meat2,
            context?.getString(R.string.meat2).toString(),250,
            250,
            78,78,
            context?.getString(R.string.meat2Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(9,
            R.drawable.meat3,
            context?.getString(R.string.meat3).toString(),250,
            250,
            78,78,
            context?.getString(R.string.meat3Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(10,
            R.drawable.meat4,
            context?.getString(R.string.meat4).toString(),250,
            250,
            75,75,
            context?.getString(R.string.meat4Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(11,
            R.drawable.meat5,
            context?.getString(R.string.meat5).toString(),250,
            250,
            75,75,
            context?.getString(R.string.meat5Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(12,
            R.drawable.meat6,
            context?.getString(R.string.meat6).toString(),250,
            250,
            60,60,
            context?.getString(R.string.meat6Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(13,
            R.drawable.meat7,
            context?.getString(R.string.meat7).toString(),250,
            250,
            75,75,
            context?.getString(R.string.meat7Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(14,
            R.drawable.meat8,
            context?.getString(R.string.meat8).toString(),250,
            250,
            75,75,
            context?.getString(R.string.meat8Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(15,
            R.drawable.meat9,
            context?.getString(R.string.meat9).toString(),250,
            250,
            78,78,
            context?.getString(R.string.meat9Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(16,
            R.drawable.meat10,
            context?.getString(R.string.meat10).toString(),250,
            250,
            78,78,
            context?.getString(R.string.meat10Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
        Meal(17,
            R.drawable.meat11,
            context?.getString(R.string.meat11).toString(),250,
            250,
            60,60,
            context?.getString(R.string.meat11Details).toString(),
            context?.getString(R.string.mealQuantityMeat).toString()
        ),
    )


    val listOfProcessedMeat: List<Meal> = listOf(
        Meal(18,
            R.drawable.kofta,
            context?.getString(R.string.kofta).toString(),500,
            500,
            80,80,
            context?.getString(R.string.koftaDetails).toString(),
            context?.getString(R.string.mealQuantityProMeat).toString(),Constants.proMeat
        ),
        Meal(19,
            R.drawable.burger,
            context?.getString(R.string.burger).toString(),500,
            500,
            85,85,
            context?.getString(R.string.burgerDetails).toString(),
            context?.getString(R.string.mealQuantityProMeat).toString(),Constants.proMeat
        )
    )
}