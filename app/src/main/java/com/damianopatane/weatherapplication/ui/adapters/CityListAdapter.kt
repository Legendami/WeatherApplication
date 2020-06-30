package com.damianopatane.weatherapplication.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.ui.viewholder.CityViewHolder

class CityListAdapter(private val clickListener: CityItemListener) : ListAdapter<List<CityItem>,
        CityViewHolder>(CityListDiffCallback()) {

    private var items: List<CityItem> = listOf()

    fun setData(list: List<CityItem>?) {
        if (list != null)
        {
            this.items = list
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        if (items.size > position) {
            holder.bind(clickListener, items[position])
        }
    }

    override fun getItemCount() = items.size
}

class CityListDiffCallback : DiffUtil.ItemCallback<List<CityItem>>() {
    override fun areItemsTheSame(oldItems: List<CityItem>, newItems: List<CityItem>): Boolean {
        return oldItems[1].id == newItems[1].id
    }

    override fun areContentsTheSame(
        oldItems: List<CityItem>,
        newItems: List<CityItem>
    ): Boolean {
        return oldItems[1].id == newItems[1].id
    }
}

class CityItemListener(val clickListener: (item: CityItem) -> Unit) {
    fun onClick(cityItem: CityItem) = clickListener(cityItem)
}