package com.dicoding.picodiploma.myviewmodel.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GithubItems(
    var githubUserImg: String? = "",
    var githubUserLogin: String? = "",
    var githubUserName: String? = "",
    var githubUserEmail: String? = "",
    var githubUserLocation: String? = "",
    var githubUserCompany: String? = "",
    var githubUserFollower: Int? = 0,
    var githubUserFollowing: Int? = 0
)  : Parcelable