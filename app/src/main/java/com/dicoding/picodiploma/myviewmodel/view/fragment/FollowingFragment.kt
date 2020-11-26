package com.dicoding.picodiploma.myviewmodel.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.adapter.FollowingGithubAdapter
import com.dicoding.picodiploma.myviewmodel.model.FollowingGithubItems
import com.dicoding.picodiploma.myviewmodel.model.GithubItems
import com.dicoding.picodiploma.myviewmodel.viewModel.FollowingViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    companion object {
        var EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<FollowingGithubItems> = ArrayList()
    private lateinit var adapter: FollowingGithubAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingGithubAdapter(listData)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra(EXTRA_DETAIL) as GithubItems
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.setHasFixedSize(true)
        rvFollowing.adapter = adapter
        followingViewModel.setData(requireActivity().applicationContext, dataUser.githubUserLogin.toString())
        showLoading(true)

        followingViewModel.getData().observe(requireActivity(), Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbFollowing.visibility = View.VISIBLE
        } else {
            pbFollowing.visibility = View.GONE
        }
    }
}