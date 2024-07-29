package com.example.vaccination

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

import com.example.vaccination.databinding.ActivityAddBinding
import java.text.SimpleDateFormat


//추가할 데이터를 입력받는 기능
class AddActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddBinding
    //file url에 대한 변수
    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvId.text = MyApplication.email


        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode === android.app.Activity.RESULT_OK){
                binding.addImageView.visibility = View.VISIBLE

                //이미지 보여주기
                Glide
                    .with(applicationContext)
                    .load(it.data?.data)

                    //크기 조정
                    .override(200,150)
                    .into(binding.addImageView)

                uri = it.data?.data!! //null이면 안됨.
            }
        }

        binding.uploadButton.setOnClickListener{
            // 갤러리 앱을 인텐트로 불러오기
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }


        binding.saveButton.setOnClickListener{
            if(binding.input.text.isNotEmpty()){
                //firestore에 저장할 수 있는 형태로 만들기
                //로그인 이메일, 스타, 한줄평, 입력시간  => 키와 값 형태로 , map
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                val data = mapOf(
                    "email" to MyApplication.email,
                    "stars" to binding.ratingBar.rating.toFloat(),
                    "comments" to  binding.input.text.toString(),
                    "date_time" to dateFormat.format(System.currentTimeMillis())
                )

                //db에 저장
                MyApplication.db.collection("comments")
                    .add(data) //comments에 추가한 값을 더함
                    .addOnSuccessListener {
                        Toast.makeText(this,"데이터 저장 성공.", Toast.LENGTH_SHORT).show()
                        //스토리지에 저장
                        uploadImage(it.id)

                        finish() //addActivity를 불렀던 곳으로 돌아감 = boardActivity
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"데이터 저장 실패.", Toast.LENGTH_SHORT).show()
                    }

            }
            else{
                Toast.makeText(this,"한줄평을 먼저 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun uploadImage(docId : String){
        val imageRef = MyApplication.storage.reference.child("images/${docId}.jpg")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            Toast.makeText(this,"사진 업로드 성공.", Toast.LENGTH_SHORT).show()
        }
        uploadTask.addOnFailureListener{
            Toast.makeText(this,"사진 업로드 실패.", Toast.LENGTH_SHORT).show()
        }
    }

}