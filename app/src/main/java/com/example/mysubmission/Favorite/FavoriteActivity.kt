package com.example.mysubmission.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission.Detail.DetailActivity
import com.example.mysubmission.adapter.UserAdapter
import com.example.mysubmission.data.local.DbModule
import com.example.mysubmission.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
        private lateinit var binding: ActivityFavoriteBinding
        private val adapter by lazy {
            UserAdapter {
                Intent(this, DetailActivity::class.java).apply {
                    putExtra("item", it)
                    startActivity(this)
                }
            }
        }
        private val viewModel by viewModels<FavoriteViewModel>() {
            FavoriteViewModel.Factory(DbModule(this))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.Favorite.layoutManager = LinearLayoutManager(this)
        binding.Favorite.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteUser().observe(this) {
            adapter.setData(it)
        }

    }
}