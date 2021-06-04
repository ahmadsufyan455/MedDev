package com.capstone.meddev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.meddev.data.NearbyPlacesResponse
import com.capstone.meddev.service.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NearbyViewModel : ViewModel(){
    private val listNearbyPlaces = MutableLiveData<NearbyPlacesResponse>()

    fun setData(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiConfig.provideApiService().getNearbyPlaces()
            if (response.isSuccessful) {
                listNearbyPlaces.postValue(response.body())
            }
        }
    }

    fun getData() : LiveData<NearbyPlacesResponse> = listNearbyPlaces
}