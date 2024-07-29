package com.example.vaccination

import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


//https://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList?serviceKey=chTIU1m5KcifQX%2FW7Wid15wx7B3mF%2BgqUl0AiUVu0gNtr793v5UdC0FjJ4696F%2Fe69tLM8pbF236wcAcas2zlA%3D%3D&numOfRows=10&pageNo=1&sickType=1&medTp=1&diseaseType=SICK_NM&searchText=%EA%B2%B0%ED%95%B5
//https://apis.data.go.kr/1790387/vcninfo/getVcnInfo?serviceKey=chTIU1m5KcifQX%2FW7Wid15wx7B3mF%2BgqUl0AiUVu0gNtr793v5UdC0FjJ4696F%2Fe69tLM8pbF236wcAcas2zlA%3D%3D&vcnCd=01

//https://www.kdca.go.kr/api/provide/healthInfo?TOKEN=[서비스키]&pageIndex=1&numOfRows=1
interface NetworkService {

        @GET("getDissNameCodeList")
        fun getXmlList(
            @Query("pageNo") pageNo: Int,
            @Query("numOfRows") numOfRows: Int,
            @Query("sickType") sickType: Int,
            @Query("medTp") medTp: Int,
            @Query("returnType") returnType: String,
            @Query("diseaseType") diseaseType: String,
            @Query("searchText") searchText: String,
            @Query("serviceKey") serviceKey: String
        ): Call<XmlResponse>


        //http://localhost/PHP_connection.php
        @GET("PHP_connection.php")
        fun getPhpList(
            @Query("age") age :String
        ) : Call<PhpResponse>


//    //다음
//        @GET("/local/geo/addr2coord")
//        fun searchAddress(
//            @Query("apikey") apiKey: String,
//            @Query("q") query: String
//        ): Call<SearchResponse>
//




}