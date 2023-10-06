package com.example.mysubmission.Detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mysubmission.Detail.follow.FollowsFragment
import com.example.mysubmission.R
import com.example.mysubmission.data.local.DbModule
import com.example.mysubmission.data.model.ItemsItem
import com.example.mysubmission.data.model.UserDetailResponse
import com.example.mysubmission.databinding.ActivityDetailBinding
import com.example.mysubmission.utils.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>(){
        DetailViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ItemsItem>("item")
        val username = item?.login ?: ""

        setFavoriteButton()
        binding.btnFav.setOnClickListener{
            viewModel.saveUser(item)
        }

        viewModel.findFav(item?.id ?: 0) {
            binding.btnFav.changeIconColor(R.color.cyan)
        }

        setUserDetail()
        viewModel.getDetail(username)

        setFragment()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.getFollowers(username)

    }

    private fun setFavoriteButton() {
        viewModel.resultAddFavorite.observe(this) {
            binding.btnFav.changeIconColor(R.color.cyan)
        }
        viewModel.resultDeleteFavorite.observe(this) {
            binding.btnFav.changeIconColor(R.color.white)
        }
    }

    private fun setFragment() {
        val fragments = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(R.string.tab_text_1),
            getString(R.string.tab_text_2)
        )
        val adapter = SectionsPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()
    }

    private fun setUserDetail() {
        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as UserDetailResponse
                    binding.ivProfile.load(user.avatarUrl) {
                        transformations(CircleCropTransformation())
                    }

                    binding.tvName.text = user.name
                    binding.tvUsername.text = user.login

                    val followersText = getString(R.string.followers_template, user.followers.toString())
                    val followingText = getString(R.string.following_template, user.following.toString())

                    binding.tvTotalFollowers.text = followersText
                    binding.tvTotalFollowing.text = followingText
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}