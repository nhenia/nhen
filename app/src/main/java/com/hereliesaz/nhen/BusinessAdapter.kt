package com.hereliesaz.nhen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BusinessAdapter(private var businesses: List<Business>) : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.business_name)
        private val addressTextView: TextView = itemView.findViewById(R.id.business_address)
        private val auraChipGroup: ChipGroup = itemView.findViewById(R.id.aura_chip_group)

        fun bind(business: Business) {
            nameTextView.text = business.name
            addressTextView.text = business.address

            // Dynamically add chips for each aura
            auraChipGroup.removeAllViews() // Clear old chips before adding new ones
            if (business.auras.isNotEmpty()) {
                auraChipGroup.visibility = View.VISIBLE
                business.auras.forEach { aura ->
                    val chip = Chip(itemView.context).apply {
                        text = aura
                        isClickable = false // Make them display-only
                    }
                    auraChipGroup.addView(chip)
                }
            } else {
                auraChipGroup.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_business, parent, false)
        return BusinessViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        holder.bind(businesses[position])
    }

    override fun getItemCount() = businesses.size

    // Function to update the list with filtered results
    fun updateList(filteredList: List<Business>) {
        businesses = filteredList
        notifyDataSetChanged()
    }
}