package com.example.bakery.ui.food.food_edit

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.loadFoodList
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class FoodEditViewModel : BaseViewModel() {
    val fStore = FirebaseFirestore.getInstance()

    val food = MutableLiveData<FoodInfo>(null)

    private fun getCurrentFood(uid: String?, callback: (FoodInfo?) -> Unit) {
        val document = uid?.let { fStore.collection("Foods").document(it) }
        document?.get()?.addOnSuccessListener { result ->
            val foodList = result.toObject(FoodInfo::class.java)
            callback(foodList)
        }
    }


    fun loadData(uid: String?) {
        getCurrentFood(uid, food::postValue)
    }

    fun updateFood(uid: String?, name: String, price: String) {
        val document = uid?.let { fStore.collection("Foods").document(it) }
        document?.apply {
            if (name.isNotBlank()) {
                update("name", name)
            }
            if (price.isNotBlank()) {
                update("price", price)
            }
        }
        loadFoodList()
    }

    fun onDeleteFood(uid: String?, onSuccess: () -> Unit) {
        val document = uid?.let { fStore.collection("Foods").document(it) }
        document?.delete()?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}