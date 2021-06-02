package com.capstone.meddev.dashboard.nearby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.meddev.data.NearbyPlacesItem
import com.capstone.meddev.databinding.RvNearbyItemLayoutBinding
import com.squareup.picasso.Picasso

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder>() {

    private val itemList : ArrayList<NearbyPlacesItem> = ArrayList()

    fun setData(list: List<NearbyPlacesItem>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RvNearbyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NearbyPlacesItem) {
            binding.addressTxt.text = data.formattedAddress
            binding.phoneNumberTxt.text = data.formattedPhoneNumber
            binding.placeNameTxt.text = data.name
            Picasso.get()
                .load(data.icon)
                .into(binding.itemImageImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvNearbyItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}