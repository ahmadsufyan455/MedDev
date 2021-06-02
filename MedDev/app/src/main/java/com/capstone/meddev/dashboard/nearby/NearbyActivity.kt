package com.capstone.meddev.dashboard.nearby

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.meddev.R
import com.capstone.meddev.databinding.ActivityNearbyBinding
import com.capstone.meddev.viewmodel.NearbyViewModel

class NearbyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNearbyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNearbyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.fasilitas_kesehatan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NearbyViewModel::class.java]

        val nearbyAdapter = NearbyAdapter()

        viewModel.setData()
        viewModel.getData().observe(this, {
            if (it.nearbyPlaces != null) {
                nearbyAdapter.setData(it.nearbyPlaces)
                binding.progressBar.visibility = View.GONE
            }
        })

        with(binding.rvHealthFacilities) {
            layoutManager = LinearLayoutManager(this@NearbyActivity)
            adapter = nearbyAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}