package com.homanad.android.githubusers.common.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This is a class that creates item decoration for a vertical Recycler View.
 * @param topSpace Distance from first item to parent
 * @param betweenSpace Distance between items
 * @param bottomSpace Distance from last item to parent
 */
class VerticalSpaceItemDecoration(
    private val topSpace: Int,
    private val betweenSpace: Int,
    private val bottomSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (getItemCount(parent) == 1) {
                top = topSpace
                bottom = bottomSpace
            } else if (parent.getChildAdapterPosition(view) == 0) {
                top = topSpace
                bottom = betweenSpace
            } else if (parent.getChildAdapterPosition(view) == getItemCount(parent) - 1)
                bottom = bottomSpace
            else {
                bottom = betweenSpace
            }
        }
    }

    private fun getItemCount(recyclerView: RecyclerView) = recyclerView.adapter?.itemCount ?: 0
}