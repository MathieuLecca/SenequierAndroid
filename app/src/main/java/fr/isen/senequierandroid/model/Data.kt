package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val dishes:List<Dish>
): Serializable

