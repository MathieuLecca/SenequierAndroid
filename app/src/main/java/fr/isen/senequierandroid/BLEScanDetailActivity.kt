package fr.isen.senequierandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.senequierandroid.databinding.ActivityBLEScanDetailBinding


class BLEScanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBLEScanDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBLEScanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}