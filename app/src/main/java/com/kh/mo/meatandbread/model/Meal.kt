package com.kh.mo.meatandbread.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kh.mo.meatandbread.util.Constants

@Entity("MEALS_TABLE")
data class Meal(
    @PrimaryKey(false) val id: Int,
    val image: Int,
    val name: String?,
    val mealQuantityValue: Int,
    val mealQuantityRate: Int,
    val price: Int,
    val mealPriceRate: Int,
    val mealDetail: String?,
    val mealQuantity: String?,
    val typeofMeal: String? = Constants.meat,
    val time: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Meal> {
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }
    }
}