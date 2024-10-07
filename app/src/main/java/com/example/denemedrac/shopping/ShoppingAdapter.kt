package com.example.denemedrac.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.denemedrac.R

data class ShoppingItem(
    val name: String,
    val description: String,
    val price: Int,
    val imageResId: Int
)

class ShoppingAdapter(private val shoppingItems: List<ShoppingItem>) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item_view, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = shoppingItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = shoppingItems.size

    class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.shopping_item_image)
        private val itemName: TextView = itemView.findViewById(R.id.shopping_item_name)
        private val itemDescription: TextView = itemView.findViewById(R.id.shopping_item_description)
        private val itemPrice: TextView = itemView.findViewById(R.id.shopping_item_price)

        fun bind(item: ShoppingItem) {
            itemImage.setImageResource(item.imageResId)
            itemName.text = item.name
            itemDescription.text = item.description
            itemPrice.text = "${item.price} ₺"
        }
    }
}
