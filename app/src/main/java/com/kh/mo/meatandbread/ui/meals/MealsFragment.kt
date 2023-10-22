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
import com.kh.mo.meatandbread.local.repo.local.Data
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.closeFragment


class MealsFragment : Fragment(), OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var title: TextView
    private lateinit var data: Data
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
        recyclerView = view.findViewById(R.id.recycle_of_meals)
        backButton = view.findViewById(R.id.back_from_meals)
        title = view.findViewById(R.id.meals_text)
        data = Data(context)
        type = MealsFragmentArgs.fromBundle(requireArguments()).type
        attachData()
        setUp()
    }

    private fun attachData() {
        meals = when (type) {
            getString(R.string.hawawshi) -> data.listOfHawawshi
            getString(R.string.meats) -> data.listOfMeat
            else -> {
                data.listOfProcessedMeat
            }
        }
        recyclerView.adapter = MealsAdapter(context, meals, this)
    }

    private fun setUp() {
        backButton.setOnClickListener { closeFragment() }
        title.text = " اصناف $type"
    }

    override fun clickMeal(position: Int) {
        findNavController().navigate(
            MealsFragmentDirections.actionMealsFragmentToMealFragment(meals[position])
        )
    }
}


