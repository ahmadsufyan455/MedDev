package com.capstone.meddev.dashboard.nearby

import android.content.Intent
import android.net.Uri
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

            binding.openMapsButton.setOnClickListener{
                val builder: Uri.Builder = Uri.Builder()
                builder.scheme("https")
                    .authority("www.google.com")
                    .appendPath("maps")
                    .appendPath("search")
                    .appendPath("")
                    .appendQueryParameter("api", "1")
                    .appendQueryParameter("query", data.name)
                val url: String = builder.build().toString()
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                it.context.startActivity(i)
            }
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