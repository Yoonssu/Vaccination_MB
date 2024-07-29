package com.example.vaccination

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//http://apis.data.go.kr/1790387/vcninfo/getVcnInfo?serviceKey=chTIU1m5KcifQX%2FW7Wid15wx7B3mF%2BgqUl0AiUVu0gNtr793v5UdC0FjJ4696F%2Fe69tLM8pbF236wcAcas2zlA%3D%3D&vcnCd=01


class RetrofitConnection {

    companion object{
        ////http://localhost/PHP_connection.php

        private const val BASE_URL_Php = "http://192.168.0.34/" //ipconfig 이용
        var phpNetworkService : NetworkService
        val phpRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_Php)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

//        init{
//            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
//        }

        //https://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList?serviceKey=chTIU1m5KcifQX%2FW7Wid15wx7B3mF%2BgqUl0AiUVu0gNtr793v5UdC0FjJ4696F%2Fe69tLM8pbF236wcAcas2zlA%3D%3D&numOfRows=10&pageNo=1&sickType=1&medTp=1&diseaseType=SICK_NM&searchText=%EA%B2%B0%ED%95%B5



        private const val BASE_URL = "https://apis.data.go.kr/B551182/diseaseInfoService/"

        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
//
        init{
                phpNetworkService = phpRetrofit.create(NetworkService::class.java)
                xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
            }

    }
}