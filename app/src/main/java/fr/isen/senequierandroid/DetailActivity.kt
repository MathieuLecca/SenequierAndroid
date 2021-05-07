 package fr.isen.senequierandroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.senequierandroid.databinding.ActivityDetailBinding
import fr.isen.senequierandroid.model.Dish

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var dish: Dish

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var quantity: Int = 0

        dish = (intent.getSerializableExtra("dish") as? Dish)!!

        if (dish != null) {

            binding.itemTitle.text = dish.title
            binding.ingredientsTextView.text = dish.getIngredients()
            binding.carousel.pageCount = dish.images.size
            binding.carousel.setImageListener(imageListener)
            Toast.makeText(this, dish.title ?: "detail", Toast.LENGTH_LONG).show()
            calculTotal(quantity, dish)
        }

        // Bouton + plus
        binding.plus.setOnClickListener {
            quantity++
            binding.count.text = quantity.toString()
            if (dish != null) {
                calculTotal(quantity, dish)
            }
        }

        // Bouton - moins
        binding.moins.setOnClickListener {
            if (quantity > 0)
                quantity--
            binding.count.text = quantity.toString()
            if (dish != null) {
                calculTotal(quantity, dish)
            }
        }
    }

    val imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            if (dish == null) {
                Picasso.get().load("https://www.google.com/search?q=carpaccio+de+saumon&client=firefox-b-d&sxsrf=ALeKk03fIsVEYRqbKKtdadI3BjHE8VL_HQ:1620414704798&tbm=isch&source=iu&ictx=1&fir=40nrQdWr5Q05rM%252Cv1_U2jy3FxpBKM%252C%252Fg%252F1q6g8_svr&vet=1&usg=AI4_-kQmhbrUsVH2YzAlmRBiyGXSj0PAJA&sa=X&ved=2ahUKEwjh1PiWo7jwAhXxDWMBHRt3DmAQ_B16BAgeEAE#imgrc=b7WWjPRV0V7dMM").into(imageView)
            } else {
                Picasso.get().load(dish.images.get(position)).into(imageView)
            }
        }
    }


   private fun calculTotal(quantity: Int, itemPricedata: Dish) {
        val total = quantity * itemPricedata.prices[0].price.toInt()
        "Total : $total €".also {
            binding.incrementPrice.text = it
        }
    }


}


