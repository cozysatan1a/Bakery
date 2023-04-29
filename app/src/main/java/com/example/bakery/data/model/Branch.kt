package com.example.bakery.data.model

data class Branch(
    var address : String? = null,
    var phone : String? = null,
    var id : String? = null
) {
    override fun toString(): String {
        return address ?: ""
    }
}