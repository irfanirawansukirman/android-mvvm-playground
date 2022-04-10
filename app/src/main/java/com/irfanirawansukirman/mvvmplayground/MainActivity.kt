package com.irfanirawansukirman.mvvmplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfanirawansukirman.mvvmplayground.databinding.ActivityMainBinding
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalSerializationApi::class)
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainAdapter = MainAdapter()
        binding.rvTitles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mainAdapter
        }

        ApiInterface.create(application)
            .getTopHeadlines("us", BuildConfig.NEWS_API_KEY)
            .enqueue(object : Callback<TopHeadlinesResponse> {
                override fun onResponse(
                    call: Call<TopHeadlinesResponse>,
                    response: Response<TopHeadlinesResponse>
                ) {
                    mainAdapter.submitData(response.body()?.getTitles().orDefault(emptyList()))
                }

                override fun onFailure(call: Call<TopHeadlinesResponse>, t: Throwable) {
                    logE(t.message.orDefault("Bandung"))
                }
            })
    }
}