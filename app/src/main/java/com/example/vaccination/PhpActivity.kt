package com.example.vaccination

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vaccination.PhpAdapter
import com.example.vaccination.PhpResponse
import com.example.vaccination.RetrofitConnection
import com.example.vaccination.databinding.ActivityPhpBinding
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = com.example.vaccination.databinding.ActivityPhpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhp.setOnClickListener{
            var age = binding.etAge.text.toString() ?:""
            val call: Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(age)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful){
                        Log.d("mobileapp","$response")
                        Log.d("mobileapp","{$response.body()}")
                        binding.phpRecyclerView.adapter = PhpAdapter(this@PhpActivity,response.body()?.result!!)
                        binding.phpRecyclerView.layoutManager = LinearLayoutManager(this@PhpActivity)
                        binding.phpRecyclerView.addItemDecoration((DividerItemDecoration(this@PhpActivity,
                            LinearLayoutManager.VERTICAL)))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Log.d("mobileapp", "onFailure")
                }
            })


        }
    }
}