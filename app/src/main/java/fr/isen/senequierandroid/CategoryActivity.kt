package fr.isen.senequierandroid

import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.textclassifier.TextLanguage
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.TintTypedArray
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.senequierandroid.HomeActivity.Companion.NOM_CATEGORY
import fr.isen.senequierandroid.Type.Companion.categoryTitle
import fr.isen.senequierandroid.databinding.ActivityCategoryBinding
import fr.isen.senequierandroid.model.MenuResults
import org.json.JSONObject


enum class Type{
    ENTREES, PLATS, DESSERTS;
    companion object{
        fun categoryTitle(type: Type?): String{
            return when (type){
                ENTREES -> "Entrées"
                PLATS -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }
}

class CategoryActivity : AppCompatActivity(), CategoryAmateur.onItemClickListener {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getSerializableExtra(HomeActivity.NOM_CATEGORY) as? Type
        binding.cellCategoryTitle.text = getcategoryTitle(categoryName)



            /*binding.categoryList.layoutManager = LinearLayoutManager(this)
         binding.categoryList.adapter = CategoryAmateur(listOf("Salade nicoise", "Salade italienne", "Salade Cesar"), this)
        */

        if (categoryName != null){
            //getData(categoryName)
        }
    }

    override fun onitemClicked(item: String){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish", item)
    }

    private fun getData(category: String?){
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val RequestView = Volley.newRequestQueue(this)
        val DataJSON = JSONObject().put("id_shop",1)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, DataJSON, {
            it ->
        Log.d("CategoryActivity","it: %s".format( it.toString()))
            val menu = Gson().fromJson(it.toString(), MenuResults::class.java)
            displayMenu(menu)
        }, { error -> error.printStackTrace() })
    RequestView.add(jsonObjectRequest)
    }

    private fun displayMenu(menu:MenuResults){
        val categoryTitleList =menu.data[1].dishes.map{it.title}
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = CategoryAmateur(categoryTitleList, this)
    }
    override fun onItemClicked(item: String) {
        TODO("Not yet implemented")
    }

    private fun getcategoryTitle(type : Type?): String {
        return when (type){
            Type.ENTREES -> "Entrées"
            Type.PLATS -> "Plats"
            Type.DESSERTS -> "Desserts"
            else -> ""
        }
    }
}