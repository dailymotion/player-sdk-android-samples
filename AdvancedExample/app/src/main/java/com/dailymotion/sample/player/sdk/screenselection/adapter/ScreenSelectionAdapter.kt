package com.dailymotion.sample.player.sdk.screenselection.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.dailymotion.sample.player.sdk.R

class ScreenSelectionAdapter(
    private val dataSet: List<ScreenSelectionItem>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ScreenSelectionAdapter.ViewHolder>() {

    companion object {
        private const val ITEM_TEXT_TYPE = 0
        private const val SECTION_TEXT_TYPE = 1
    }
    interface ItemClickListener {
        fun onItemClicked(item: ScreenSelectionItem)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val layoutId = when (viewType) {
            SECTION_TEXT_TYPE -> R.layout.item_screen_selection_text_section
            else -> R.layout.item_screen_selection_text_row
        }
        // Create a new view, which defines the UI of the list item
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = dataSet[position]

        viewHolder.textView.text = item.displayText()
        val resources = viewHolder.itemView.resources
        viewHolder.itemView.updateLayoutParams<RecyclerView.LayoutParams> {
            topMargin = computeMarginTop(item, resources)
        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataSet[position]
        return when (item) {
            is SectionText -> SECTION_TEXT_TYPE
            else -> ITEM_TEXT_TYPE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun computeMarginTop(item: ScreenSelectionItem, resources: Resources): Int {

        val isPlayerAPISectionItem = isPlayerAPISectionItem(item = item, resources = resources)
        return if (isPlayerAPISectionItem) {
            resources.getDimension(R.dimen.section_item_big_margin_top).toInt()
        } else {
            resources.getDimension(R.dimen.section_item_default_margin_top).toInt()
        }
    }

    private fun isPlayerAPISectionItem(item: ScreenSelectionItem, resources: Resources): Boolean {
        return item is SectionText && item.text == resources.getString(R.string.player_api)
    }
}