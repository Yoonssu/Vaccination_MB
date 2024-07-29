package com.example.vaccination

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.vaccination.databinding.ActivityEnroll2Binding
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class Enroll2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEnroll2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        //알림
        val permissionLauncher = registerForActivityResult( //사용자가 허용시 알림을 보냄.
            ActivityResultContracts.RequestMultiplePermissions() ) {
            if (it.all { permission -> permission.value == true }) {
                noti()
            }
            else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }


        // main activity에서 값 가져옴
        var date = intent.getStringExtra("today") //값이 전달됨

        binding.date.text = date  //id : date 자리에 내가 가져온 값을 넣겠다.

        // 백 버튼을 addactivity의 홈화면에 표시하겠다. 밑에 onSupportNavigateUp 함수 오버라이드
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //addactivity의 홈화면에서 처리하겟다

        binding.btnSave.setOnClickListener {
            val intent = Intent()

            // EditText 값 가져오기
            val vaccineType = binding.addEditView1.text.toString()
            val vaccineDate = binding.addEditView2.text.toString()
            val medicalInstitution = binding.addEditView3.text.toString()
            val otherSideEffects = binding.editTextTextMultiLine.text.toString()

            // CheckBox 값 가져오기
            val hasFever = binding.checkBox.isChecked
            val hasLocalReaction = binding.checkBox2.isChecked
            val hasHeadache = binding.checkBox3.isChecked
            val hasVomiting = binding.checkBox5.isChecked
            val hasMusclePain = binding.checkBox4.isChecked

            // 값들을 Intent에 추가
            intent.putExtra("vaccineType", vaccineType)
            intent.putExtra("vaccineDate", vaccineDate)
            intent.putExtra("medicalInstitution", medicalInstitution)
            intent.putExtra("otherSideEffects", otherSideEffects)
            intent.putExtra("hasFever", hasFever)
            intent.putExtra("hasLocalReaction", hasLocalReaction)
            intent.putExtra("hasHeadache", hasHeadache)
            intent.putExtra("hasVomiting", hasVomiting)
            intent.putExtra("hasMusclePain", hasMusclePain)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //33버전 이후부터
                if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                }
                else {
                    permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                }
            }
            else { //33이전
                noti()
            }



            //db에 저장하고 되돌아감
            // db에 저장하고 되돌아감
            val db = DBHelper(this).writableDatabase
            val sql = """
                INSERT INTO todo_tb 
                (vaccineType, vaccineDate, medicalInstitution, otherSideEffects, hasFever, hasLocalReaction, hasHeadache, hasVomiting, hasMusclePain) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """
            db.execSQL(
                sql,
                arrayOf(vaccineType, vaccineDate, medicalInstitution, otherSideEffects, if (hasFever) 1 else 0, if (hasLocalReaction) 1 else 0, if (hasHeadache) 1 else 0, if (hasVomiting) 1 else 0, if (hasMusclePain) 1 else 0)
            )
            db.close()


            //file에 저장
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

            //파일에 저장하기 위햐서는 파일에 대한 포인터 필요
            //파일저장된 폴더의 주소, 파일의 이름
            //filesDir 현재 이 앱에 대한 내장 폴더 제공
            val file = File(filesDir ,"test.txt")
            val writestream: OutputStreamWriter = file.writer()  //writestream 가져와서
            writestream.write(dateFormat.format((System.currentTimeMillis()))) //파일에 씀
            writestream.flush() //파일에 최종적으로 쓰기 위함



            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    fun noti(){ //퍼미션 만드는 과정
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder //알림을 만들 수 있는 빌더 생성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     // 26 버전 이상
            //각 채널 별로 알림 보냄, 한 번에 여러 알림 동시에 보낼 수 있음.
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {   // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)  // 앱 런처 아이콘 상단에 숫자 배지를 표시할지 여부를 지정 , 상태바에 알림 왔는지 표시 여부.
                //알림은 발생하되, 소리는 발생하는 알림을 만든다면 70~75 삭제 -> 오류날때.
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder() //소리 넣는 방법
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes) //알림 소리
                enableVibration(true) //알림 진동
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)
            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {  // 26 버전 미만 -> 바로 빌더 생성 가능
            builder = NotificationCompat.Builder(this)
        }

        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.drawable.small) //알림창에 뜨는 아이콘을 나타냄
            setWhen(System.currentTimeMillis()) //현재 시간
            setContentTitle("등록")
            setContentText("나의 정보가 등록되었습니다.")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big)) //이미지를 띄움.
        }

        manager.notify(11, builder.build())
    }


    //백 버튼 눌렀을 때,즉, mainactivity로 다시 돌아갈때,
    override fun onSupportNavigateUp(): Boolean {
        //아무것도 입력하지 않고 백버튼 눌렀을 때, 기존에 있던 내용이 삭제되지 않음
        //add 에서 main으로 돌아갈 때 콜백함수 올바르게 실행됨

        val intent = intent
        setResult(Activity.RESULT_OK,intent)

        finish()
        return true
    }

}