package com.dicoding.picodiploma.myviewmodel.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.myviewmodel.model.FollowerGithubItems
import com.dicoding.picodiploma.myviewmodel.model.FollowingGithubItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class FollowerViewModel : ViewModel() {

    private val listGithubs = MutableLiveData<ArrayList<FollowerGithubItems>>()
    private val listItems = ArrayList<FollowerGithubItems>()

    fun setData(context: Context, githubUserLogin: String) {
        val url = "https://api.github.com/users/$githubUserLogin/followers"
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 40f6d8eda1ec9b3b6de802600e5bb4dd2d0a9e1f")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val list = JSONArray(result)
                    if (list.isNull(0)){
                        val githubItems = FollowerGithubItems()
                        githubItems.githubFollowerImg = "https://icon-library.com/images/img-icon/img-icon-1.jpg"
                        githubItems.githubFollowerLogin = "No Following"
                        githubItems.githubFollowerName = "No Following"
                        listItems.add(githubItems)
                        listGithubs.postValue(listItems)
                    }
                    else {
                        for (i in 0 until list.length()) {
                            val github = list.getJSONObject(i)
                            setGithubDetail(github.getString("login"), context)
                        }
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
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()            }
        })
    }

    fun setGithubDetail(githubUserLogin: String, context: Context) {
        val url = "https://api.github.com/users/$githubUserLogin"
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 40f6d8eda1ec9b3b6de802600e5bb4dd2d0a9e1f")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)

                try {
                    val githubItems = FollowerGithubItems()
                    val responseObject = JSONObject(result)
                    val githubUserImg = responseObject.getString("avatar_url")
                    var githubUserName = responseObject.getString("name")

                    if (githubUserName == "null") {
                        githubUserName = "-"
                    }

                    githubItems.githubFollowerImg = githubUserImg
                    githubItems.githubFollowerLogin = githubUserLogin
                    githubItems.githubFollowerName = githubUserName

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

    fun getData(): LiveData<ArrayList<FollowerGithubItems>> {
        return listGithubs
    }

}
