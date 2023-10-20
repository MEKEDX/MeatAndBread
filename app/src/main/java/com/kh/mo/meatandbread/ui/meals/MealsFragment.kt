package com.kh.mo.meatandbread.ui.meals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.Data


class MealsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = Data(context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle_of_meals)
        val adapter = MealsAdapter(context,data.listOfMeat)
        recyclerView.adapter = adapter
    }

}