package com.capstone.meddev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.meddev.data.ArticleResponse
import com.capstone.meddev.service.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val listArticle = MutableLiveData<List<ArticleResponse>>()

    fun setData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiConfig.provideApiService().getArticle()
            if (response.isSuccessful) {
                listArticle.postValue(response.body()?.list_article)
            }
        }
    }

    fun getData(): LiveData<List<ArticleResponse>> = listArticle
}