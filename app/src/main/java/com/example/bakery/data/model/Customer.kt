package com.example.bakery.data.model

data class Customer(
    var name : String? = null,
    var gender : String? = null,
    var address : String? = null,
    var dob : String? = null,
    var phone : String? = null,
    var id : String? = null,
    var bill: ArrayList<Bill?>? = null
)
