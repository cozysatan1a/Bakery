package com.example.bakery.ui.branch.branch_data

import com.example.bakery.data.model.Customer
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BranchDataViewModel : BaseViewModel() {
    fun getData(
        branchId: String,
        callback: (MutableList<String>, Int, MutableList<FoodRevenueByMonth>) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Customers")
            .get()
            .addOnSuccessListener { documents ->
                val timeList = mutableListOf<String>()
                val foodRevenueList = mutableListOf<FoodRevenueByMonth>()
                var totalIncome = 0
                for (document in documents) {
                    val customer = document.toObject(Customer::class.java)
                    val bills =
                        customer.bill?.filter { it?.branch == branchId && it.completed == true }
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
                                val foodRevenue = foodRevenueList.find { it.foodName == foodName && it.monthYear == monthYear }
                                if (foodRevenue != null) {
                                    foodRevenue.revenue += quantity
                                } else {
                                    foodRevenueList.add(FoodRevenueByMonth(foodName, monthYear, quantity))
                                }
                            }
                        }

                    }
                }
                callback.invoke(timeList, totalIncome, foodRevenueList)

            }
    }
}

data class FoodRevenueByMonth(val foodName: String, val monthYear: String, var revenue: Int)
