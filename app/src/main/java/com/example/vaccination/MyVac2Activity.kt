package com.example.vaccination

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vaccination.databinding.ActivityMyVac2Binding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat


class MyVac2Activity : AppCompatActivity() {
    lateinit var binding: ActivityMyVac2Binding
    lateinit var adapter: MyAdapter

    // 리사이클러 뷰에 저장될 데이터를 모아두는 변수 선언, 계속해서 추가 mutable
    var datas: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 디폴트
        binding = ActivityMyVac2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // db값 가져오기
        datas = mutableListOf<String>() // db 초기화
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)  // db에 있는 값 cursor로 옴

        // 리사이클러뷰로 출력
        while (cursor.moveToNext()) { // 다음 값이 존재한다면
            datas?.add(cursor.getString(1))
        }
        db.close()

//        // 프로그램 실행 중에 데이터가 삭제되지 않도록 하는 것
//        datas = savedInstanceState?.let { // 전달받은 번들에 있는 값 사용
//            it.getStringArrayList("datas")?.toMutableList()
//        } ?: mutableListOf()


        //파일전부 삭제하고 이 부분 주석처리후 add item 하고, 하나 add item 한 후에 주석 풀고 추가
        //리사이클러뷰에 추가 이외에
        //파일읽기
        val file = File(filesDir ,"test.txt") //파일에 대한 변수
        //이 변수를 읽어오기 위해
        val readstream: BufferedReader = file.reader().buffered() // 읽을 준비됨
        //출력
        binding.lastsaved.text = "마지막 저장시간: " + readstream.readLine() // 한줄씩 읽어오기


        // 1. adapter
        adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // MyAdapter(datas)

        // 데코 layout manager
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager // 그대로 수직으로 배치

        // 아이템 데코레이션 추가
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            ) // 항목 추가될 때마다 밑에 가로로 줄 긋기, 구별하는 역할
        )

        val requestLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                result.data?.let { data ->
                    val vaccineType = data.getStringExtra("vaccineType")
                    val vaccineDate = data.getStringExtra("vaccineDate")
                    val medicalInstitution = data.getStringExtra("medicalInstitution")
                    val otherSideEffects = data.getStringExtra("otherSideEffects")
                    val hasFever = data.getBooleanExtra("hasFever", false)
                    val hasLocalReaction = data.getBooleanExtra("hasLocalReaction", false)
                    val hasHeadache = data.getBooleanExtra("hasHeadache", false)
                    val hasVomiting = data.getBooleanExtra("hasVomiting", false)
                    val hasMusclePain = data.getBooleanExtra("hasMusclePain", false)

                    val newEntry = """
                    백신 종류: $vaccineType
                    예방접종 날짜: $vaccineDate
                    의료기관: $medicalInstitution
                    부작용:
                      발열: ${if (hasFever) "있음" else "없음"}
                      국소반응: ${if (hasLocalReaction) "있음" else "없음"}
                      두통: ${if (hasHeadache) "있음" else "없음"}
                      구토: ${if (hasVomiting) "있음" else "없음"}
                      근육통: ${if (hasMusclePain) "있음" else "없음"}
                    기타: $otherSideEffects
                """

                    datas?.add(newEntry)
                    adapter.notifyDataSetChanged()
                }
            }

        // activity 끼리의 전달 => intent 사용
        // activity add에서 저장한 값을 main activity에 가져오겠다
        // mainFab은 main activity의 아이디
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, Enroll2Activity::class.java)

            // 오늘 날짜 가져오기
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))

            // startActivity(intent)
            requestLauncher.launch(intent) // 호출하는 방법
        }

        while (cursor.moveToNext()) {
            datas?.add(cursor.getString(1))
        }
        Log.d("MyVac2Activity", "Loaded datas: $datas")


    }



    // 프로그램 실행 중에 데이터가 삭제되지 않도록 저장하는 것 번들에
    // 화면을 회전하면 액티비티가 종료되었다가 나오는 데, 번들에 저장하면 삭제되지 않고 그대로 남아있음
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(
            "datas",
            ArrayList(datas)
        ) // datas라는 변수에 ArrayList(datas)이 형식으로 저장하겠다.
    }
}
