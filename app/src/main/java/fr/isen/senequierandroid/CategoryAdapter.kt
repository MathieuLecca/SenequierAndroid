package fr.isen.senequierandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.senequierandroid.databinding.CellCategoryBinding
import fr.isen.senequierandroid.model.Dish


class CategoryAdapter(private val categories: List<Dish>, private val clickListener: CategoryActivity) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private lateinit var _binding:CellCategoryBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val binding = CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _binding=binding
        return CategoryViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val dish = categories[position]
        holder.cellTitle.text = categories[position].title

        holder.layout.setOnClickListener {
            clickListener.onItemClicked(categories[position])
        }
        if (categories[position].getFirstPicture().isNullOrEmpty()){
            holder.image.setImageResource(R.drawable.carpaccio_de_saumon)
        }
        else {
            Picasso.get().load(categories[position].getFirstPicture())
                .into(holder.image)
        }

        holder.price.text = "${categories[position].prices[0].price} €"
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        val cellTitle: TextView = view.findViewById(R.id.cellCategoryTitle)
        val layout = view.findViewById<View>(R.id.CellLayout)
        val image: ImageView = view.findViewById<View>(R.id.cellImageView) as ImageView
        val price : TextView = view.findViewById(R.id.tvPrix)
    }

    /*fun bind (dish: Dish){
        Picasso.get()
                .load(dish.getFirstPicture())
                .into (_binding.cellImageView)
    }*/

    interface onItemClickListener{
        fun onItemClicked(item: Dish)
    }
}