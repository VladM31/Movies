package huge.of.movies.ui.decorations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import huge.of.movies.R

class LineDecoration(context: Context) : DividerItemDecoration(context, VERTICAL) {
    private val dividerHeight = 3
    private val paint = Paint()

    init {
        val color = ContextCompat.getColor(context, R.color.gray)
        paint.color = color
        paint.isAntiAlias = true
        ContextCompat.getDrawable(context, android.R.color.transparent)?.let {
            setDrawable(it)
        }
     }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        parent.adapter?.itemCount?.let {
            if (position < it - 1) {
                outRect.bottom = dividerHeight
            }
        }
    }
}