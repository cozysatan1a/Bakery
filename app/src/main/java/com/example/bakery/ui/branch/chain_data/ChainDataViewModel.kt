package com.example.bakery.ui.branch.chain_data

import com.example.bakery.data.model.Customer
import com.example.bakery.ui.base.BaseViewModel
import com.example.bakery.ui.branch.branch_data.FoodRevenueByMonth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ChainDataViewModel : BaseViewModel() {
    val db = FirebaseFirestore.getInstance()
    fun getData(callback: (MutableList<String>, Int, MutableList<FoodRevenueByMonth>) -> Unit) {
        val branchIds = mutableListOf<String>()
        db.collection("Branches")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    branchIds.add(document.id)
                }
                // Use branchIds here
                db.collection("Customers")
                    .get()
                    .addOnSuccessListener { documents ->
                        val timeList = mutableListOf<String>()
                        val foodRevenueList = mutableListOf<FoodRevenueByMonth>()
                        var totalIncome = 0
                        for (document in documents) {
                            val customer = document.toObject(Customer::class.java)
                            val bills =
                                customer.bill?.filter { it?.branch in branchIds && it?.completed == true }
                            bills?.forEach { bill ->
                                val completeTime = bill?.completeTime
                                val monthYear = completeTime?.substring(0, 7)
                                if (monthYear != null && monthYear !in timeList) {
                                    timeList.add(monthYear)
                                }
                                totalIncome += bill?.price!!.toInt()
                                bill.order?.forEach { foodOrder ->
                                    val foodName = foodOrder?.food?.name
                                    val quantity = foodOrder?.quantity ?: 0
                                    if (foodName != null && monthYear != null) {
                                        val foodRevenue =
                                            foodRevenueList.find { it.foodName == foodName && it.monthYear == monthYear }
                                        if (foodRevenue != null) {
                                            foodRevenue.revenue += quantity
                                        } else {
                                            foodRevenueList.add(
                                                FoodRevenueByMonth(
                                                    foodName,
                                                    monthYear,
                                                    quantity
                                                )
                                            )
                                        }
                                    }
                                }

                            }
                        }
                        callback.invoke(timeList, totalIncome, foodRevenueList)

                    }
            }

    }
}