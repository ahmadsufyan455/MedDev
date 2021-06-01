package com.capstone.meddev.dashboard.article

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.meddev.R
import com.capstone.meddev.databinding.ActivityArticleBinding
import com.capstone.meddev.viewmodel.ArticleViewModel

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.artikel_kesehatan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ArticleViewModel::class.java]
        val articleAdapter = ArticleAdapter()

        viewModel.setData()
        viewModel.getData().observe(this, {
            if (it != null) {
                articleAdapter.setData(it)
                binding.progressBar.visibility = View.GONE
            }
        })

        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(this@ArticleActivity)
            adapter = articleAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}