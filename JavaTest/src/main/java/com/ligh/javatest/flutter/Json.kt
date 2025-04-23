package com.example.compat

//sadfafsd
@Serializable
data class Company(
    @SerializedName("company_name")
    val name: String,
    val founder: User? // 可空嵌套对象
)

/**
 * asdfad
 */
@Serializable
data class Ste(
    @SerializedName("ste_name")
    val name: String,
    val founder: User? // 可空嵌套对象
)