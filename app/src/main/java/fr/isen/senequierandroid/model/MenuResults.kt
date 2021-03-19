package fr.isen.senequierandroid.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MenuResults(@SerializedName("data") val data: List<Data>)