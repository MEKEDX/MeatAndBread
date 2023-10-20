package com.kh.mo.meatandbread.local

import android.content.Context
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Meal

class Data(context: Context?) {
    val listOfHawawshi: List<Meal> = listOf(
        Meal(R.drawable.haw3, context?.getString(R.string.hwa1).toString(),
            1,
            20,
            context?.getString(R.string.hwa1Details).toString()),
        Meal(R.drawable.haw7, context?.getString(R.string.hwa2).toString(),1,25,
            context?.getString(R.string.hwa2Details).toString() ),
        Meal(R.drawable.haw2, context?.getString(R.string.hwa3).toString(),1,30,
            context?.getString(R.string.hwa3Details).toString() ),
        Meal(R.drawable.haw8, context?.getString(R.string.hwa4).toString(),1,30,
            context?.getString(R.string.hwa4Details).toString() ),
        Meal(R.drawable.haw4, context?.getString(R.string.hwa5).toString(),1,30,
            context?.getString(R.string.hwa5Details).toString() ),
        Meal(R.drawable.haw5, context?.getString(R.string.hwa6).toString(),1,35,
            context?.getString(R.string.hwa6Details).toString() ),
    )


    val listOfMeat: List<Meal> = listOf(
        Meal(R.drawable.meat1, context?.getString(R.string.meat1).toString(),250,78,context?.getString(R.string.meat1Details).toString()),
        Meal(R.drawable.meat2, context?.getString(R.string.meat2).toString(),250,78,context?.getString(R.string.meat2Details).toString()),
        Meal(R.drawable.meat3, context?.getString(R.string.meat3).toString(),250,78,context?.getString(R.string.meat3Details).toString()),
        Meal(R.drawable.meat4, context?.getString(R.string.meat4).toString(),250,75,context?.getString(R.string.meat4Details).toString()),
        Meal(R.drawable.meat5, context?.getString(R.string.meat5).toString(),250,75,context?.getString(R.string.meat5Details).toString()),
        Meal(R.drawable.meat6, context?.getString(R.string.meat6).toString(),250,60,context?.getString(R.string.meat6Details).toString()),
        Meal(R.drawable.meat7, context?.getString(R.string.meat7).toString(),250,75,context?.getString(R.string.meat7Details).toString()),
        Meal(R.drawable.meat8, context?.getString(R.string.meat8).toString(),250,75,context?.getString(R.string.meat8Details).toString()),
        Meal(R.drawable.meat9, context?.getString(R.string.meat9).toString(),250,78,context?.getString(R.string.meat9Details).toString()),
        Meal(R.drawable.meat10, context?.getString(R.string.meat10).toString(),250,78,context?.getString(R.string.meat10Details).toString()),
        Meal(R.drawable.meat11, context?.getString(R.string.meat11).toString(),250,60,context?.getString(R.string.meat11Details).toString()),
    )


    val listOfProcessedMeat: List<Meal> = listOf(
        Meal(R.drawable.kofta, context?.getString(R.string.kofta).toString(),500,
            80,context?.getString(R.string.koftaDetails).toString()),
        Meal(R.drawable.burger, context?.getString(R.string.burger).toString(),500,85,
            context?.getString(R.string.burgerDetails).toString())
    )
}