package com.kh.mo.meatandbread.ui.meals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.Data
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.ui.meal.MealPresenter
import com.kh.mo.meatandbread.util.closeFragment


class MealsFragment : Fragment() ,MealsView{

    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var title: TextView
    private lateinit var mealsPresenterView: MealsPresenterView
    private lateinit var mealsPresenter: MealsPresenter
    private lateinit var type: String
    private lateinit var meals: List<Meal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)
        setUp()
    }
    private fun inti(view: View){
        recyclerView = view.findViewById(R.id.recycle_of_meals)
        backButton = view.findViewById(R.id.back_from_meals)
        title = view.findViewById(R.id.meals_text)
        type = MealsFragmentArgs.fromBundle(requireArguments()).type
        mealsPresenter=MealsPresenter(RepositoryIm(LocalSource.getInstance(requireContext())), this)
        mealsPresenterView=mealsPresenter


    }

    private fun setUp() {
        backButton.setOnClickListener { closeFragment() }
        title.text = StringBuilder().apply {
            append("أصناف ")
            append(type)
        }
    }

     private fun clickMeal(position: Int) {
        findNavController().navigate(
            MealsFragmentDirections.actionMealsFragmentToMealFragment(meals[position])
        )
    }

    override fun getMeals(meals: List<Meal>) {
        this.meals=meals
        recyclerView.adapter = MealsAdapter(context, meals,::clickMeal )
    }

    override fun onResume() {
        super.onResume()
        mealsPresenterView.getMeals(type)
    }
}


