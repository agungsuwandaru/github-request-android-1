package com.dicoding.picodiploma.myviewmodel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.adapter.SectionsPagerAdapter
import com.dicoding.picodiploma.myviewmodel.model.GithubItems

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as GithubItems
        textName.text = dataUser.githubUserName
        textEmail.text = dataUser.githubUserEmail
        textLocation.text = dataUser.githubUserLocation
        textCompany.text = dataUser.githubUserCompany
        textFollowing.text = dataUser.githubUserFollowing.toString()
        textFollower.text = dataUser.githubUserFollower.toString()
        Glide.with(this@DetailActivity).load(dataUser.githubUserImg).into(img_user_avatar)
        supportActionBar?.title = dataUser.githubUserLogin

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

    }

}
