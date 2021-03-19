package fr.isen.senequierandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.senequierandroid.databinding.CellCategoryBinding

class CategoryAmateur(private val categories: List<String>, private val clickListener: CategoryActivity) : RecyclerView.Adapter<CategoryAmateur.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAmateur.CategoryViewHolder {
        val binding = CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: CategoryAmateur.CategoryViewHolder, position: Int) {
        holder.cellTitle.text = categories[position]
        holder.layout.setOnClickListener{
            //clickListener.onitemClicked(Categories[position].toString())
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        val cellTitle: TextView = view.findViewById(R.id.cellCategoryTitle)
        val layout = view.findViewById<View>(R.id.CellLayout)

    }

    interface onItemClickListener{
        fun onItemClicked(item: String)
        fun onitemClicked(item: String)
    }
}