package com.dicoding.picodiploma.myviewmodel.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myviewmodel.model.GithubItems
import com.dicoding.picodiploma.myviewmodel.viewModel.MainViewModel
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.adapter.GithubAdapter
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var listAdapter: GithubAdapter
    private lateinit var mainViewModel: MainViewModel
    private var listDataUsers: ArrayList<GithubItems> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listAdapter = GithubAdapter(listDataUsers)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        listAdapter.notifyDataSetChanged()
        recyclerView.adapter = listAdapter
        searchData()
        configMainViewModel(listAdapter)
    }

    private fun searchData() {
        btnGithubUserLogin.setOnClickListener {
            listDataUsers.clear()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            listAdapter.notifyDataSetChanged()
            recyclerView.adapter = listAdapter
            val githubUserLogin = editGithubUserLogin.text.toString()
            mainViewModel.setGithub(githubUserLogin, applicationContext)
            showLoading(true)
            configMainViewModel(listAdapter)
        }
    }

    private fun configMainViewModel(adapter: GithubAdapter) {
        mainViewModel.getGithubs().observe(this, Observer { githubItems ->
            if (githubItems != null) {
                listAdapter.setData(githubItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
