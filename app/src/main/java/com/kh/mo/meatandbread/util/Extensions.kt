package com.kh.mo.meatandbread.util

import android.icu.text.NumberFormat
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import java.util.*


fun Fragment.closeFragment() {
    this.findNavController().popBackStack()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeInVisible() {
    this.visibility = View.INVISIBLE
}
fun Int.convertToArabicFormat(): String {
    val nf: NumberFormat = NumberFormat.getInstance(Locale("ar", "EG"))
  return  nf.format(this).toString()
}

fun String.convertToEnglishFormat(): Int {
    val nf: NumberFormat = NumberFormat.getInstance(Locale("ar", "EG"))
    val parsedNumber = nf.parse(this)
    return parsedNumber.toInt()
}