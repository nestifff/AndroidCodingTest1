package com.example.androidcodingtest1.app.list

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingtest1.R
import com.example.androidcodingtest1.app.list.model.ListItem

class MyListAdapter(
    private val onItemClick: (ListItem) -> Unit
) :
    ListAdapter<ListItem, MyListAdapter.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }


    class ViewHolder(
        view: View,
        private val onItemClick: (ListItem) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val textView: TextView
        private val contentContainer: ViewGroup

        init {
            textView = view.findViewById(R.id.textView)
            contentContainer = view.findViewById(R.id.contentContainer)
        }

        fun onBind(item: ListItem) {
            this.itemView.background = ColorDrawable(item.color)
            textView.text = item.text
            contentContainer.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    private class UserDiffCallBack : DiffUtil.ItemCallback<ListItem>() {

        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            oldItem == newItem
    }
}
