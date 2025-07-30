package com.hereliesaz.nhen

data class Business(
    val id: String,
    val name: String,
    val address: String,
    val category: String,
    val auras: List<String> = emptyList()
)