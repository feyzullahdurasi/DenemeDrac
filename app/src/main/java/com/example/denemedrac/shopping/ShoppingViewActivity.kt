package com.example.denemedrac.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.denemedrac.R

class ShoppingViewActivity : AppCompatActivity() {

    private lateinit var shoppingRecyclerView: RecyclerView
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_view)

        // Initialize RecyclerView
        shoppingRecyclerView = findViewById(R.id.shopping_recycler_view)
        shoppingRecyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data for the shopping list
        val shoppingItems = listOf(
            ShoppingItem("Telefon Tutucu", "Magsafe özelliğine sahip telefon tutucu", 190, R.drawable.ic_launcher_background),
            ShoppingItem("Kulaklık", "Bluetooth özellikli kablosuz kulaklık", 250, R.drawable.ic_launcher_background),
            ShoppingItem("Powerbank", "10,000mAh taşınabilir şarj cihazı", 120, R.drawable.ic_launcher_background)
        )

        // Set adapter with data
        shoppingAdapter = ShoppingAdapter(shoppingItems)
        shoppingRecyclerView.adapter = shoppingAdapter
    }
}
