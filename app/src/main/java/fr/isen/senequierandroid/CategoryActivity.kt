package fr.isen.senequierandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.senequierandroid.databinding.ActivityCategoryBinding
import fr.isen.senequierandroid.model.Dish
import fr.isen.senequierandroid.model.MenuResults
import org.json.JSONObject


enum class Type {
    ENTREES, PLATS, DESSERTS;

    companion object {
        fun categoryTitle(type: Type?): String {
            return when (type) {
                ENTREES -> "Entrées"
                PLATS -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }
}


class CategoryActivity : AppCompatActivity(), CategoryAdapter.onItemClickListener {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = (intent.getSerializableExtra(HomeActivity.NOM_CATEGORY) as? Type)
        binding.cellCategoryTitle.text = getcategoryTitle(categoryName)


        /*binding.categoryList.layoutManager = LinearLayoutManager(this)
     binding.categoryList.adapter = CategoryAmateur(listOf("Salade nicoise", "Salade italienne", "Salade Cesar"), this)
    */

        if (categoryName != null) {
            getData(categoryName)
        }
    }

    override fun onItemClicked(item: Dish) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish", item)
        startActivity(intent)
    }


    private fun getData(category: Type) {
        Log.d("getData", "Entered getData")
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val RequestView = Volley.newRequestQueue(this)
        val DataJSON = JSONObject().put("id_shop", 1)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, DataJSON, { it ->
            Log.d("CategoryActivity", "it: %s".format(it.toString()))
            val menu = Gson().fromJson(it.toString(), MenuResults::class.java)
            displayMenu(menu, category)
        }, { error ->
            error.printStackTrace()
            Log.d("CategoryActivity", "$error")
        })
        RequestView.add(jsonObjectRequest)
    }

    private fun displayMenu(menu: MenuResults, categoryName: Type) {
        var nb: Int = -1
        when (categoryName){
            Type.ENTREES -> nb = 0
            Type.PLATS -> nb = 1
            Type.DESSERTS -> nb = 2
        }
        if (nb != -1){
            val categoryTitleList = menu.data[nb].dishes
            Log.d("Result : ", "$categoryTitleList")
            binding.categoryList.layoutManager = LinearLayoutManager(this)
            binding.categoryList.adapter = CategoryAdapter(categoryTitleList, this)
        }
    }


    private fun getcategoryTitle(type: Type?): String {
        return when (type) {
            Type.ENTREES -> "Entrées"
            Type.PLATS -> "Plats"
            Type.DESSERTS -> "Desserts"
            else -> ""
        }
    }
}
