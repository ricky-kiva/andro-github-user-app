package com.rickyslash.githubuserapp.ui.detailuser.follows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.githubuserapp.api.response.DetailFollowsResponseItem
import com.rickyslash.githubuserapp.databinding.DetailFollowsFragmentBinding
import com.rickyslash.githubuserapp.ui.detailuser.DetailUserActivity

class DetailFollowsFragment : Fragment() {

    private lateinit var binding: DetailFollowsFragmentBinding
    private val detailFollowsViewModel by viewModels<DetailFollowsViewModel>()

    private fun showUserDetails(data: DetailFollowsResponseItem) {
        val moveUserDetailsIntent = Intent(requireContext(), DetailUserActivity::class.java)
        moveUserDetailsIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
        startActivity(moveUserDetailsIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFollowsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME)

        if (index != null && username != null) {
            detailFollowsViewModel.getFollows(username, index)
        }

        detailFollowsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailFollowsViewModel.listFollows.observe(viewLifecycleOwner) {
            if (it != null) {
                setFollowsData(it)
            }
        }

        detailFollowsViewModel.isError.observe(viewLifecycleOwner) {
            connectionErrorCheck(it)
        }
    }

    private fun setFollowsData(followsData: List<DetailFollowsResponseItem>) {
        val followsAdapter = FollowsAdapter(followsData)
        binding.rvFollows.adapter = followsAdapter
        binding.rvFollows.layoutManager = LinearLayoutManager(requireContext())

        followsAdapter.setOnItemClickCallback(object : FollowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailFollowsResponseItem) {
                showUserDetails(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBarDetailFollows.visibility = View.VISIBLE else binding.progressBarDetailFollows.visibility = View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

    private fun connectionErrorCheck(isError: Boolean) {
        if (isError) Toast.makeText(requireContext(), "There's an error connecting to the server", Toast.LENGTH_SHORT).show()
    }
}