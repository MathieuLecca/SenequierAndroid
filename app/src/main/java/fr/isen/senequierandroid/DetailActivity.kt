package fr.isen.senequierandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import fr.isen.senequierandroid.databinding.ActivityCategoryBinding
import fr.isen.senequierandroid.databinding.ActivityDetailBinding
import fr.isen.senequierandroid.model.Dish

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var dish: Dish

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dish = (intent.getSerializableExtra("dish") as? Dish)!!

        if (dish != null) {
            binding.itemTitle.text = dish.title
            binding.ingredientsTextView.text = dish.getIngredients()
            binding.carousel.pageCount = dish.images.size
            binding.carousel.setImageListener(imageListener)
        }
        if (dish != null) {
            Toast.makeText(this, dish.title ?: "detail", Toast.LENGTH_LONG).show()
        }
    }

    val imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            if (dish == null) {
                Picasso.get().load("http://www.estdebol.ru/images/no_photo.png").into(imageView)
            } else {
                Picasso.get().load(dish.images.get(position)).into(imageView)
            }
        }
    }
}