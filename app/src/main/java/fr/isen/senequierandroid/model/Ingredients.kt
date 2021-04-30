package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Ingredients(@SerializedName("id") val id : String,
                       @SerializedName("name_fr") val name_fr : String) : Serializable
