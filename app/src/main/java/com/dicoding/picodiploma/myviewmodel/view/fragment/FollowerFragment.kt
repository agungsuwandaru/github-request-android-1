package com.dicoding.picodiploma.myviewmodel.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myviewmodel.viewModel.FollowerViewModel
import com.dicoding.picodiploma.myviewmodel.adapter.FollowerGithubAdapter
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.model.FollowerGithubItems
import com.dicoding.picodiploma.myviewmodel.model.GithubItems
import kotlinx.android.synthetic.main.fragment_follower.*
import androidx.lifecycle.Observer
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log

class FollowerFragment : Fragment() {

    companion object {
        var EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<FollowerGithubItems> = ArrayList()
    private lateinit var adapter: FollowerGithubAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowerGithubAdapter(listData)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra(EXTRA_DETAIL) as GithubItems
        rvFollower.layoutManager = LinearLayoutManager(activity)
        rvFollower.setHasFixedSize(true)
        rvFollower.adapter = adapter
        Log.d("GITHUB4", "follower")
        followerViewModel.setData(requireActivity().applicationContext, dataUser.githubUserLogin.toString())
        showLoading(true)

        followerViewModel.getData().observe(requireActivity(), Observer { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbFollower.visibility = View.VISIBLE
        } else {
            pbFollower.visibility = View.GONE
        }
    }

}