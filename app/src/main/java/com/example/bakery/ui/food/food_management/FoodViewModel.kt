package com.example.bakery.ui.food.food_management

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.FoodOrder
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.reflect.KFunction1

@HiltViewModel
class FoodViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    val foods = MutableLiveData<MutableList<FoodInfo>>(null)


    private fun getAllFoods(callback: (MutableList<FoodInfo>) -> Unit) {
        fStore.collection("Foods").get().addOnSuccessListener { result->
            val foodList = mutableListOf<FoodInfo>()
            for (document in result) {
                val foods = document.toObject(FoodInfo::class.java)
                foodList.add(foods)
            }
            callback(foodList)
        }
    }

    fun loadData() {
        getAllFoods(foods::postValue)
    }
}