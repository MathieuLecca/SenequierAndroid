package fr.isen.senequierandroid

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import fr.isen.senequierandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entree = findViewById<Button>(R.id.entree)
        binding.entree.setOnClickListener{
            Toast.makeText(this, "bouton entree", Toast.LENGTH_SHORT).show()
            StartCategoryActivity(Type.ENTREES)
        }

        binding.plat.setOnClickListener{
            Toast.makeText(this, "bouton plat", Toast.LENGTH_SHORT).show()
            StartCategoryActivity(Type.PLATS)
        }

        binding.dessert.setOnClickListener{
            Toast.makeText(this, "bouton dessert", Toast.LENGTH_SHORT).show()
            StartCategoryActivity(Type.DESSERTS)
        }

        binding.Ble.setOnClickListener{

            val i: Intent = Intent(this, BLEScanActivity::class.java)
            startActivity(i)
        }

        /*val entree = findViewById<Button>(R.id.entree)
        val entree = findViewById<Button>(R.id.entree)*/



    }

    private fun StartCategoryActivity(type: Type){
        val i: Intent = Intent(this, CategoryActivity::class.java)
        i.putExtra(NOM_CATEGORY, type)
        startActivity(i)
    }

    companion object {
        const val NOM_CATEGORY = "NOM_CATEGORY"
    }
}



