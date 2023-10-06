package com.example.mysubmission.Detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission.Detail.DetailViewModel
import com.example.mysubmission.adapter.UserAdapter
import com.example.mysubmission.data.model.ItemsItem
import com.example.mysubmission.databinding.FragmentFollowBinding
import com.example.mysubmission.utils.Result

class FollowsFragment : Fragment() {
    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter {

        }
    }

    private  val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }
        when(type) {
            FOLLOWERS -> {
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<ItemsItem>)

            }
            is Result.Error -> {
                Toast.makeText(requireActivity(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowsFragment()
            .apply {
                this.type = type
            }

    }
}