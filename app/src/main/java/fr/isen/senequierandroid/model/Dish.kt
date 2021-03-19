package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName

data class Dish (
    @SerializedName ("name_fr") val title: String,
    @SerializedName("items") val dishes:List<Dish>
)
