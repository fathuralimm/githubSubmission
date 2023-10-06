package com.example.mysubmission.setting

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mysubmission.data.local.SettingPreferences
import com.example.mysubmission.databinding.ActivityFavoriteBinding
import com.example.mysubmission.databinding.ActivitySettingBinding

class SettingActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    private val  viewModel by viewModels<SettingViewModel>{
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setThemeSetting()
    }

    private fun setThemeSetting() {
        viewModel.getTheme().observe(this) {
            if (it) {
                binding.switchTheme.text = "Dark Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.switchTheme.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}