package com.capstone.meddev.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NearbyPlacesItem(

	@field:SerializedName("formatted_address")
	val formattedAddress: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("formatted_phone_number")
	val formattedPhoneNumber: String? = null
) : Parcelable