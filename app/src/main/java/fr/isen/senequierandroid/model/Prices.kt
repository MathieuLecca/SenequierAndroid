package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Prices(
    @SerializedName("price") val price: String
) : Serializable
