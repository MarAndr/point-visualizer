package com.example.pointvisualizer.ui.graph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pointvisualizer.R
import com.example.pointvisualizer.features.points.entities.Point

class PointsAdapter(private var points: List<Point>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerXTextView: TextView = itemView.findViewById(R.id.headerX)
        val headerYTextView: TextView = itemView.findViewById(R.id.headerY)
    }

    inner class PointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val xTextView: TextView = itemView.findViewById(R.id.xTextView)
        val yTextView: TextView = itemView.findViewById(R.id.yTextView)
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
        if (holder is PointViewHolder) {
            val point = points[position - 1] // -1 так как первый элемент — это заголовок
            holder.xTextView.text = point.x.toString()
            holder.yTextView.text = point.y.toString()
        } else if (holder is HeaderViewHolder) {
            holder.headerXTextView.text = "X"
            holder.headerYTextView.text = "Y"
        }
    }

    override fun getItemCount(): Int = points.size + 1

    fun updateData(newPoints: List<Point>) {
        val diffCallback = PointsDiffCallback(points, newPoints)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        points = newPoints
        diffResult.dispatchUpdatesTo(this)
    }
}

class PointsDiffCallback(
    private val oldList: List<Point>,
    private val newList: List<Point>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].x == newList[newItemPosition].x &&
                oldList[oldItemPosition].y == newList[newItemPosition].y
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
