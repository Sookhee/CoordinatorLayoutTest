package com.example.coordinatorlayout

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class SimpleStringRecyclerViewAdapter(private val mValues: List<String>) :
    RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mBoundString: String? = null
        val mView: View
        val mTextView: TextView
        override fun toString(): String {
            return super.toString() + " '" + mTextView.text
        }

        init {
            mView = view
            mTextView = view.findViewById(R.id.text1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBoundString = mValues[position]
        holder.mTextView.text = mValues[position]
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}