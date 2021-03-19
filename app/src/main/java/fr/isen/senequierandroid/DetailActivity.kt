package fr.isen.senequierandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.senequierandroid.model.Dish

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dish = intent.getSerializableExtra("dish") as? Dish
        val dishName = dish?.title

        Toast.makeText(this, dishName ?: "", Toast.LENGTH_LONG).show()
    }
}