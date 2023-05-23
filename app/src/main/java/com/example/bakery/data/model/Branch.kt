package com.example.bakery.data.model

data class Branch(
    var address : String? = null,
    var phone : String? = null,
    var id : String? = null,
    var feedback : List<Feedback>? = null
) {
    override fun toString(): String {
        return address ?: ""
    }
}

data class Feedback(
    var customer: String? = null,
    var feedback: String? = null
)