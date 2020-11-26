package com.dicoding.picodiploma.myviewmodel.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.myviewmodel.model.GithubItems
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.view.DetailActivity
import kotlinx.android.synthetic.main.github_items.view.*

import java.util.ArrayList

class GithubAdapter(private val mData: ArrayList<GithubItems>) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {

    fun setData(items: ArrayList<GithubItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): GithubViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.github_items, viewGroup, false)
        return GithubViewHolder(mView)
    }

    override fun onBindViewHolder(githubViewHolder: GithubViewHolder, position: Int) {
        githubViewHolder.bind(mData[position])
        val data = mData[position]
        githubViewHolder.itemView.setOnClickListener{
            val dataUserIntent = GithubItems(
                    data.githubUserImg,
                    data.githubUserLogin,
                    data.githubUserName,
                    data.githubUserEmail,
                    data.githubUserLocation,
                    data.githubUserCompany,
                    data.githubUserFollower,
                    data.githubUserFollowing
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(mIntent)
            }
        }

    override fun getItemCount(): Int = mData.size

    inner class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(githubItems: GithubItems) {
            with(itemView){
                textUserLogin.text = githubItems.githubUserLogin
                textUserName.text = githubItems.githubUserName
                Glide.with(this).load(githubItems.githubUserImg).into(img_photo)
            }
        }
    }


}





