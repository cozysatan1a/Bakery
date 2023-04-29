package com.example.bakery.data.model

import com.google.firebase.firestore.FirebaseFirestore

data class Bill(
    var order: List<FoodOrder>? = null,
    var price: String? = null,
    var completed: Boolean? = null,
    var completeTime: String? = null
)

data class FoodOrder(
    var food: FoodInfo? = null,
    var quantity: Int? = null
)

data class FoodInfo(
    var id: String? = null,
    var name: String? = null,
    var price: String? = null
) {
    override fun toString(): String {
        return name ?: ""
    }
}

val foodList = mutableListOf<FoodInfo>()

val db = FirebaseFirestore.getInstance()
val foodCollection = db.collection("Foods") // get a reference to the food collection

fun loadFoodList() {
    foodList.clear()
    foodCollection.get()
        .addOnSuccessListener { result -> //
            for (document in result) {
                val id = document.getString("id")
                val name = document.getString("name")
                val price = document.getString("price")
                if (name != null && price != null) {
                    val food = FoodInfo(id, name, price)
                    foodList.add(food)
                }
            }
        }
        .addOnFailureListener { exception -> // if the operation fails
            println(exception.message) // print the error message
        }
}