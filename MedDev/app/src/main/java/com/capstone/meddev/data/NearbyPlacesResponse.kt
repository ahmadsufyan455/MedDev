package com.capstone.meddev.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NearbyPlacesResponse(
	@field:SerializedName("Nearby_places")
	val nearbyPlaces: List<NearbyPlacesItem>? = null
) : Parcelable