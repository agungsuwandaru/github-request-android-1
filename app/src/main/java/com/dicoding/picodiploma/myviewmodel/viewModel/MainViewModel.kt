package com.dicoding.picodiploma.myviewmodel.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.myviewmodel.model.GithubItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*


class MainViewModel : ViewModel() {

    private val listGithubs = MutableLiveData<ArrayList<GithubItems>>()
    private val listItems = ArrayList<GithubItems>()

    fun setGithub(githubUserLogin: String, context: Context) {
        val url = "https://api.github.com/search/users?q=$githubUserLogin"
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 40f6d8eda1ec9b3b6de802600e5bb4dd2d0a9e1f")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?) {
                val result = String(responseBody!!)
                try {
                    listItems.clear()
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val github = list.getJSONObject(i)
                        val githubUserLogin = github.getString("login")
                        setGithubDetail(githubUserLogin, context)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setGithubDetail(githubUserLogin: String, context: Context) {
        val url = "https://api.github.com/users/$githubUserLogin"
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 40f6d8eda1ec9b3b6de802600e5bb4dd2d0a9e1f")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?) {
                val result = String(responseBody!!)

                try {
                    val githubItems = GithubItems()
                    val responseObject = JSONObject(result)
                    val githubUserImg = responseObject.getString("avatar_url")
                    val githubUserName = responseObject.getString("name")
                    var githubUserEmail = responseObject.getString("email")
                    var githubUserLocation = responseObject.getString("location")
                    var githubUserCompany = responseObject.getString("company")
                    val githubUserFollowing = responseObject.getInt("following")
                    val githubUserFollower = responseObject.getInt("followers")

                    if (githubUserEmail == "null") {
                        githubUserEmail = "-"
                    }

                    if (githubUserLocation == "null") {
                        githubUserLocation = "-"
                    }

                    if (githubUserCompany == "null") {
                        githubUserCompany = "-"
                    }

                    githubItems.githubUserImg = githubUserImg
                    githubItems.githubUserLogin = githubUserLogin
                    githubItems.githubUserName = githubUserName
                    githubItems.githubUserEmail = githubUserEmail
                    githubItems.githubUserLocation = githubUserLocation
                    githubItems.githubUserCompany = githubUserCompany
                    githubItems.githubUserFollower = githubUserFollower
                    githubItems.githubUserFollowing = githubUserFollowing

                    listItems.add(githubItems)
                    listGithubs.postValue(listItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getGithubs(): LiveData<ArrayList<GithubItems>> {
        return listGithubs
    }
}
