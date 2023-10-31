package com.kh.mo.meatandbread.ui.cart

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.model.WayOfSend
import com.kh.mo.meatandbread.ui.waiting.SaveTimer
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.isTimeClear
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.time
import com.kh.mo.meatandbread.util.Constants
import com.kh.mo.meatandbread.util.convertToArabicFormat
import com.kh.mo.meatandbread.util.makeGone
import com.kh.mo.meatandbread.util.makeVisible
import java.text.FieldPosition

class CartFragment : Fragment(), OnClickListenerCart, CartFragmentView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartPresenter: CartPresenter
    private lateinit var cartPresenterView: CartPresenterView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var meals: List<Meal>
    private lateinit var checkoutValue: TextView
    private lateinit var checkout: Button
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var dialog: AlertDialog
    private var totalTime: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)
        setUp()


    }

    private fun inti(view: View) {
        recyclerView = view.findViewById(R.id.recycle_of_meals_cart)
        checkoutValue = view.findViewById(R.id.checkout_value)
        checkout = view.findViewById(R.id.checkout)
        lottieAnimationView = view.findViewById(R.id.lottie_animation_no_product)

    }

    private fun setUp() {
        checkoutValue.text = "0"
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter
        cartPresenter = CartPresenter(
            RepositoryIm(LocalSource.getInstance(requireContext())),
            this
        )

        checkout.setOnClickListener {
            if (!isCartListEmpty()) {
                if (SaveTimer.customPreference(
                        requireContext(), Constants.PREFERENCE_NAME
                    ).isTimeClear == true
                ) {
                    showWeekDialog()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "من فضلك انتظر حتي انتهاء طلبك الاول",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(requireActivity(), "اضف للسلة بعض المشتريات", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        cartPresenterView = cartPresenter
        cartPresenterView.getAllMeals()
        cartPresenterView.getTotalPrice()
        cartPresenterView.getTotalTime()
    }

    override fun clickCartMeal(meal: Meal) {
        findNavController().navigate(
            CartFragmentDirections.actionCartToMealFragment(meal)
        )
    }

    override fun clickPlus(
        meal: Meal,
        mealQuantityValue: Int,
        mealPrice: Int
    ) {
        cartPresenterView.clickPlus(
            meal, mealQuantityValue, mealPrice, "+"
        ) { n1: Int, n2: Int -> n1 + n2 }
    }

    override fun clickMinus(meal: Meal, mealQuantityValue: Int, mealPrice: Int) {
        cartPresenterView.clickMinus(
            meal, mealQuantityValue, mealPrice, "-"
        ) { n1: Int, n2: Int -> n1 - n2 }

    }

    override fun deleteMeal(meal: Meal) {
        cartPresenterView.deleteMeal(meal)

    }

    override fun getMeals(meals: List<Meal>) {
        if (meals.isEmpty()) {
            lottieAnimationView.makeVisible()
        } else {
            lottieAnimationView.makeGone()
        }
        cartAdapter.submitList(meals)
        this.meals = meals
    }


    override fun deleteDone() {}

    override fun updateDone() {
    }

    override fun getTotalPrice(totalPrice: Int) {
        if (totalPrice != 0) {
            checkoutValue.makeVisible()
            checkoutValue.text = totalPrice.convertToArabicFormat()
        } else {
            checkoutValue.makeGone()
        }
    }

    override fun getTotalTime(totalTime: Int) {
        this.totalTime = totalTime
    }

    private fun isCartListEmpty() = recyclerView.isEmpty()

    private fun navigateToTimer() {

        findNavController().navigate(
            CartFragmentDirections.actionCartToWaiting(totalTime)
        )
    }


    private fun showWeekDialog() {

        dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_dialog_of_send_message))
            .setView(makeViewForDialog())
            .show()

    }

    private fun makeViewForDialog(): View {
        val customView = LayoutInflater.from(context).inflate(R.layout.send_message, null)
        val iconStringList = customView.findViewById<RecyclerView>(R.id.iconStringList)
        iconStringList.adapter =
            MessagesAdapter(requireContext(), dataOfDialog(), this::onClickMessage)

        return customView
    }

    private fun onClickMessage(position: Int) {
        when (position) {
            0 -> openWhatsApp(getMessage())
            1 -> openMessenger(getMessage())
            2 -> openSMS(getMessage())

        }


    }

    private fun dataOfDialog() = arrayOf(
        WayOfSend(getString(R.string.WhatApp), R.drawable.whatsapp_),
        WayOfSend(getString(R.string.Messenger), R.drawable.messenger),
        WayOfSend(getString(R.string.SMS), R.drawable.sms)
    )

    private fun getMessage(): String {

        return getString(R.string.start_of_message) + " \n " + meals.joinToString("\n") { meal ->
            getString(
                R.string.message_of_request,
                meal.mealQuantityValue.convertToArabicFormat(),
                meal.mealQuantityUnit,
                meal.name,
                meal.price.convertToArabicFormat()
            )
        } + " \n " + getString(R.string.end_of_message, checkoutValue.text)


    }

    private fun openWhatsApp(message: String) {
        try {
            dialog.dismiss()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                Uri.parse("https://wa.me/+201119112723?text=$message"),
                "text/plain"
            )
            startActivity(intent)
            navigateToTimer()

        } catch (ex: ActivityNotFoundException) {

        }
    }

    private fun openMessenger(message: String) {
        try {
            dialog.dismiss()

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.facebook.orca")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                message
            )
            startActivity(intent)
            navigateToTimer()

        } catch (ex: ActivityNotFoundException) {

        }
    }

    private fun openSMS(message: String) {
        dialog.dismiss()

        val countryCode = "+2"
        val phoneNumber = "01119112723"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("smsto:$countryCode$phoneNumber")
        intent.putExtra("sms_body", message)
        startActivity(intent)
        navigateToTimer()
    }

}