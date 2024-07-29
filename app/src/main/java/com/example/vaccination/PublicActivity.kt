package com.example.vaccination

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vaccination.databinding.ActivityPublicBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicBinding
    private lateinit var xmlAdapter: XmlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색 버튼 클릭 시
        binding.btnSearch.setOnClickListener {

            val disease = binding.edtName.text.toString()

            val call: Call<XmlResponse> = RetrofitConnection.xmlNetServ.getXmlList(
                1,
                10,
                1,
                1,
                "xml",
                "SICK_NM",
                disease,
                "chTIU1m5KcifQX/W7Wid15wx7B3mF+gqUl0AiUVu0gNtr793v5UdC0FjJ4696F/e69tLM8pbF236wcAcas2zlA==" //디코딩
            )

            call.enqueue(object : Callback<XmlResponse> {
                override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                    if(response.isSuccessful){
                        Log.d("mobileapp", "Response: ${response.body()}")
                        // 이 부분이 잘 호출되는지 확인합니다.
                        if (response.body() != null) {
                            val items = response.body()!!.body.items.item
                            binding.xmlRecyclerView.adapter = XmlAdapter(items)
                            binding.xmlRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.xmlRecyclerView.addItemDecoration(
                                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
                            )
                        } else {
                            Log.e("mobileapp", "Response body is null")
                        }
                    } else {
                        Log.e("mobileapp", "Response not successful: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                    Log.e("mobileapp", "onFailure: ${t.message}")
                }
            })
        }
    }

}
