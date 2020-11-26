package com.dicoding.picodiploma.myviewmodel.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.myviewmodel.model.FollowerGithubItems
import com.dicoding.picodiploma.myviewmodel.R
import kotlinx.android.synthetic.main.github_items.view.*

import java.util.ArrayList

class FollowerGithubAdapter (private val mData: ArrayList<FollowerGithubItems>) : RecyclerView.Adapter<FollowerGithubAdapter.GithubViewHolder>() {

    fun setData(items: ArrayList<FollowerGithubItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followerGithubItems: FollowerGithubItems) {
            with(itemView){
                textUserLogin.text = followerGithubItems.githubFollowerLogin
                textUserName.text =  followerGithubItems.githubFollowerName
                Glide.with(itemView.context).load(followerGithubItems.githubFollowerImg).into(img_photo)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): GithubViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.github_items_follower, viewGroup, false)
        return GithubViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(githubViewHolder: GithubViewHolder, position: Int) {
        githubViewHolder.bind(mData[position])
    }

}





