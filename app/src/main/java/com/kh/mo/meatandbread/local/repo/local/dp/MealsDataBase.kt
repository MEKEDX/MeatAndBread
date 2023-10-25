package com.kh.mo.meatandbread.local.repo.local.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kh.mo.meatandbread.model.Meal

@Database(entities = [Meal::class], version = 1)
 abstract class MealsDataBase :RoomDatabase() {
    abstract fun mealDao(): MealsDao

    companion object {

        @Volatile private var instanse: MealsDataBase? = null
        fun getInstance(context: Context?): MealsDataBase {
            return instanse ?: synchronized(this) {
                buildDataBase(context).also {instanse=it  }
            }
        }
        private fun buildDataBase(context: Context?): MealsDataBase{
           return Room.databaseBuilder(
                context?.applicationContext!!,
                MealsDataBase::class.java,
                "MEAL_DATABASE"
            ).build()
        }
    }
}