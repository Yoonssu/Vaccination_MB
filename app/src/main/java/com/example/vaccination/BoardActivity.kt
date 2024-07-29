package com.example.vaccination

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vaccination.databinding.ActivityBoardBinding
import com.google.firebase.firestore.Query

//버튼 클릭했을때 데이터를 불러오는 기능
class BoardActivity : AppCompatActivity() {

    //viewBinding
    lateinit var binding: ActivityBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) { //-> 다음 onStart 실행.
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else {
                Toast.makeText(this, "인증을 먼저 진행해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()

        if (MyApplication.checkAuth()) {
            MyApplication.db.collection("comments")
                .orderBy("date_time", Query.Direction.DESCENDING)
                .get() //컬렉션 가져오기
                .addOnSuccessListener {result->
                    val itemList = mutableListOf<ItemData>()
                    for(document in result){ //document 하나씩 itemList에 넣어줌
                        val item = document.toObject(ItemData::class.java)

                        //id저장 별도
                        item.docId = document.id

                        itemList.add(item)

                        binding.recyclerView.layoutManager = LinearLayoutManager(this)
                        binding.recyclerView.adapter = BoardAdapter(this, itemList)
                    }

                }
                .addOnFailureListener{
                    Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }
}