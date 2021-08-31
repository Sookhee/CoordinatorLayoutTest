package com.example.coordinatorlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerView.adapter =
            SimpleStringRecyclerViewAdapter(ItemData.stringList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}