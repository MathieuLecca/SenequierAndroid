package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName
import fr.isen.senequierandroid.model.Ingredients
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    @SerializedName("prices") val prices: List<Prices>,
    @SerializedName("ingredients") val ingredients: List<Ingredients>,
    @SerializedName("images") val images: List<String>

) : Serializable {


    fun getFirstPicture() = if (!images.isNullOrEmpty()) {
        if (images.isNotEmpty() && images[0].isNotEmpty()) {
            images[0]
        } else {
            null
        }
    }else {
        null
    }



fun getIngredients(): String = ingredients.map(Ingredients::name_fr).joinToString( " , " )
}