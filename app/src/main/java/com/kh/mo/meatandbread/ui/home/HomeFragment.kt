package com.kh.mo.meatandbread.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.kh.mo.meatandbread.R

class HomeFragment : Fragment() {

    private lateinit var hawawshi: CardView
    private lateinit var meat: CardView
    private lateinit var processed_meat: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hawawshi=view.findViewById(R.id.hawawshi)
        meat=view.findViewById(R.id.meat)
        processed_meat=view.findViewById(R.id.processed_meat)
        setUp()
    }


    private fun setUp(){
        hawawshi.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.
            actionHomeToMealsFragment(getString(R.string.hawawshi)))
        }
        meat.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.
            actionHomeToMealsFragment(getString(R.string.meats)))
        }
        processed_meat.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.
            actionHomeToMealsFragment(getString(R.string.pro_meat)))
        }
    }


}