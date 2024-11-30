package com.example.pointvisualizer.ui.graph

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(private val dividerWidth: Int, private val color: Int) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint().apply {
            color = this@CustomDividerItemDecoration.color
            strokeWidth = dividerWidth.toFloat()
            style = Paint.Style.FILL
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            // Рисуем горизонтальные разделители
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = parent.paddingLeft.toFloat()
            val right = (parent.width - parent.paddingRight).toFloat()
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + dividerWidth

            c.drawRect(left, top, right, bottom, paint)

            // Рисуем вертикальные разделители
            val dividerLeft = child.right.toFloat()
            val dividerRight = dividerLeft + dividerWidth
            c.drawRect(dividerLeft, child.top.toFloat(), dividerRight, child.bottom.toFloat(), paint)
        }
    }
}
