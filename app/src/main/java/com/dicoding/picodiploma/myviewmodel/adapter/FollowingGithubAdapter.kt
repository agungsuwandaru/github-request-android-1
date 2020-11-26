package com.dicoding.picodiploma.myviewmodel.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.myviewmodel.R
import com.dicoding.picodiploma.myviewmodel.model.FollowingGithubItems
import kotlinx.android.synthetic.main.github_items.view.*

import java.util.ArrayList

class FollowingGithubAdapter (private val mData: ArrayList<FollowingGithubItems>) : RecyclerView.Adapter<FollowingGithubAdapter.GithubViewHolder>() {

    fun setData(items: ArrayList<FollowingGithubItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followingGithubItems: FollowingGithubItems) {
            with(itemView){
                textUserLogin.text = followingGithubItems.githubFollowingLogin
                textUserName.text =  followingGithubItems.githubFollowingName
                Glide.with(itemView.context).load(followingGithubItems.githubFollowingImg).into(img_photo)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): GithubViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.github_items_following, viewGroup, false)
        return GithubViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(githubViewHolder: GithubViewHolder, position: Int) {
        githubViewHolder.bind(mData[position])
    }

}





