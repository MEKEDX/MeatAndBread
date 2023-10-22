package com.kh.mo.meatandbread.ui.meal


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.RepositoryIm
import com.kh.mo.meatandbread.local.repo.local.LocalSource
import com.kh.mo.meatandbread.model.Meal
import com.kh.mo.meatandbread.util.Constants
import com.kh.mo.meatandbread.util.closeFragment
import kotlin.properties.Delegates


class MealFragment : Fragment(), MealFragmentView {

    private lateinit var mealImage: ImageView
    private lateinit var backButton: ImageView
    private lateinit var shareMeal: ImageView
    private lateinit var mealName: TextView
    private lateinit var mealQuantity: TextView
    private lateinit var mealQuantityMinus: ImageView
    private lateinit var mealQuantityValue: TextView
    private lateinit var mealQuantityPlus: ImageView
    private lateinit var mealPrice: TextView
    private lateinit var mealDetails: TextView
    private lateinit var addToBasket: Button
    private lateinit var mainMeal: Meal
    private lateinit var mealPresenter: MealPresenter
    private lateinit var mealPresenterView: MealPresenterView
    private var rate by Delegates.notNull<Int>()
    private var price by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)
        setUp()
        receiveData()
        addDataToBasket()
        putLimitOfQuantity()

    }

    private fun inti(view: View) {
        mealImage = view.findViewById(R.id.meal_image)
        backButton = view.findViewById(R.id.back_from_meal)
        shareMeal = view.findViewById(R.id.share_meal)
        mealName = view.findViewById(R.id.meal_name)
        mealQuantity = view.findViewById(R.id.meal_quantity)
        mealQuantityMinus = view.findViewById(R.id.productQuantityMinus)
        mealQuantityValue = view.findViewById(R.id.meal_quantity_rate)
        mealQuantityPlus = view.findViewById(R.id.productQuantityPlus)
        mealPrice = view.findViewById(R.id.meal_price)
        mealDetails = view.findViewById(R.id.meal_details)
        addToBasket = view.findViewById(R.id.add_to_basket)
        mealPresenter =
            MealPresenter(RepositoryIm(LocalSource.getInstance(requireContext())), this)
        mealPresenterView = mealPresenter
    }

    private fun receiveData() {
        mainMeal = MealFragmentArgs.fromBundle(requireArguments()).meal

        attachData(mainMeal)
        mealPresenterView.getMeal(mainMeal.id)

    }

    private fun addDataToBasket(){
        addToBasket.setOnClickListener {
            mealPresenterView.saveMeal(
                mainMeal.copy(
                    price = mealPrice.text.toString().toInt(),
                    mealQuantityValue =  mealQuantityValue.text.toString().toInt()
                )
            )
        }
    }
    private fun setUp(){
        backButton.setOnClickListener { closeFragment() }
        shareMeal.setOnClickListener { shareProduct() }
    }

    private fun attachData(meal: Meal) {
        rate = mainMeal.mealQuantityRate
        price = mainMeal.mealPriceRate
        mealImage.setImageResource(meal.image)
        mealName.text = meal.name
        mealQuantity.text = meal.mealQuantity
        mealDetails.text = meal.mealDetail
        mealQuantityValue.text = meal.mealQuantityValue.toString()
        mealPrice.text = meal.price.toString()



    }

    private fun putLimitOfQuantity() {
        val rateLimits = mapOf(
            Constants.meat to Pair(250, 5000),
            Constants.hwa to Pair(1, 12),
            Constants.proMeat to Pair(500, 5000)
        )
        val (minLimit, maxLimit) = rateLimits[mainMeal.typeofMeal] ?: Pair(0, 0)
        mealQuantityMinus.setOnClickListener {
            var tempR=0
            var tempP=0
            if (mealQuantityValue.text.toString().toInt()  != minLimit) {
                tempR=mealQuantityValue.text.toString().toInt()-rate
                tempP=mealPrice.text.toString().toInt()-price
                mealQuantityValue.text = tempR.toString()
                mealPrice.text = tempP.toString()


            }
        }
        mealQuantityPlus.setOnClickListener {
            var tempR=0
            var tempP=0
            if (mealQuantityValue.text.toString().toInt() < maxLimit) {
                tempR=rate+mealQuantityValue.text.toString().toInt()
                tempP=price+mealPrice.text.toString().toInt()
                mealQuantityValue.text = tempR.toString()
                mealPrice.text = tempP.toString()
            }
        }
    }

    private fun shareProduct() {
        val intent = Intent(Intent.ACTION_SEND)
        val shareBody =
            getString(
                R.string.shareProduct,
                mainMeal.name,
                mainMeal.price.toDouble()
            )
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(intent)
    }

    override fun getMeal(meal: Meal) {
        attachData(meal)
    }

    override fun savedDone() {
        Toast.makeText(requireActivity(), " تمت الإضافه الي المشتريات", Toast.LENGTH_SHORT).show()
    }

}