package com.example.pointvisualizer.ui.graph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pointvisualizer.R
import com.example.pointvisualizer.features.points.entities.Point
import java.text.NumberFormat
import java.util.Locale

class PointsAdapter : ListAdapter<Point, RecyclerView.ViewHolder>(PointsDiffCallback()) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    inner class PointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val xTextView: TextView = itemView.findViewById(R.id.xTextView)
        private val yTextView: TextView = itemView.findViewById(R.id.yTextView)

        fun bind(point: Point) {
            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            xTextView.text = numberFormat.format(point.x)
            yTextView.text = numberFormat.format(point.y)
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerXTextView: TextView = itemView.findViewById(R.id.headerX)
        private val headerYTextView: TextView = itemView.findViewById(R.id.headerY)

        fun bind() {
            val context = itemView.context
            headerXTextView.text = context.getString(R.string.header_x)
            headerYTextView.text = context.getString(R.string.header_y)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent, false)
            PointViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PointViewHolder -> {
                val point = getItem(position - 1)
                holder.bind(point)
            }
            is HeaderViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }
}

class PointsDiffCallback : DiffUtil.ItemCallback<Point>() {
    override fun areItemsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem.x == newItem.x && oldItem.y == newItem.y
    }

    override fun areContentsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem == newItem
    }
}
