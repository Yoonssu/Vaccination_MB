package com.example.vaccination

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
//import com.android.identity.android.legacy.Utility
import com.example.vaccination.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.util.Timer
import java.util.TimerTask
//import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var viewPager: ViewPager2
    lateinit var imagePagerAdapter: ImagePagerAdapter
    lateinit var imageList: List<Int>

    //헤더
    lateinit var headerView : View

    //설정값 저장하기 위한 변수
    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        //설정값 반영
//        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
//        // sharedPreference 안에있는 각각의 값 불러오기
//
//        val textcolor = sharedPreference.getString("color", "#000000") //id 불러오듯이 불러옴
//        binding.t3.setTextColor(Color.parseColor(textcolor))
//
//        val color = sharedPreference.getString("backgroundcolor", "#80b9ba") //id 불러오듯이 불러옴
//        binding.drawer.setBackgroundColor(Color.parseColor(color))
//
////        val idStr = sharedPreference.getString("id","")
////        binding.t1.text = idStr
//
//
//        val size = sharedPreference.getString("size","13.0f")
//        binding.t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())


        // DrawerLayout Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // Drawer 메뉴
        //이거 빼먹으면 리스너 안 먹음
        binding.mainDrawerView.setNavigationItemSelectedListener(this)


        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener{
            Log.d("mobileapp","button.setOnClickListener")
            val intent = Intent(this, AuthActivity::class.java)

            if(button.text.equals("로그인")){
                intent.putExtra("status","logout")
            }else if(button.text.equals("로그아웃")){
                intent.putExtra("status","login")
            }

            startActivity(intent)
            binding.drawer.closeDrawers()
        }


        // ImageButton 클릭 리스너 설정
        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, PublicActivity::class.java)
            startActivity(intent)
        }

        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        imageButton2.setOnClickListener {
            val intent = Intent(this, MyVac2Activity::class.java)
            startActivity(intent)
        }

        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            val intent = Intent(this, NotiActivity::class.java)
            startActivity(intent)
        }

        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }

        val imageButton5: ImageButton = findViewById(R.id.imageButton5)
        imageButton5.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
        val imageButton6: ImageButton = findViewById(R.id.imageButton6)
        imageButton6.setOnClickListener {
            val intent = Intent(this, PhpActivity::class.java)
            startActivity(intent)
        }

        // 메인화면 이미지 슬라이드
        viewPager = findViewById(R.id.viewPager)

        // 이미지 리스트 설정 (임의의 이미지 리소스 예시)
        imageList = listOf(R.drawable.ban1, R.drawable.ban2, R.drawable.ban3)

        // 어댑터 생성 및 설정
        imagePagerAdapter = ImagePagerAdapter(this, imageList)
        viewPager.adapter = imagePagerAdapter

        // 자동 슬라이딩 설정 (필요시)
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val currentItem = viewPager.currentItem
                    val totalItems = imagePagerAdapter.itemCount
                    val nextItem = (currentItem + 1) % totalItems
                    viewPager.currentItem = nextItem
                }
            }
        }, 3000, 3000) // 3초마다 자동 슬라이딩


        //Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")

//        //카카오 로그인
//        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
        //YCdAPz8hPzM/Pz9fPw0K



    }

    // DrawerLayout Toggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        if(item.itemId===R.id.menu_main_setting){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
            return true
        }

        if(item.itemId===R.id.item_board){
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
            binding.drawer.closeDrawers()
            return true
        }



        return super.onOptionsItemSelected(item)
    }

    // Option menu를 위한 오버라이드 함수
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        //설정
        menuInflater.inflate(R.menu.menu_setting, menu)

        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, "$query 검색합니다.", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResumeFragments() {


        //설정값 반영
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // sharedPreference 안에있는 각각의 값 불러오기

        val textcolor = sharedPreference.getString("color", "#000000") //id 불러오듯이 불러옴
        binding.t3.setTextColor(Color.parseColor(textcolor))

        val color = sharedPreference.getString("backgroundcolor", "#80b9ba") //id 불러오듯이 불러옴
        binding.drawer.setBackgroundColor(Color.parseColor(color))

//        val idStr = sharedPreference.getString("id","")
//        binding.t1.text = idStr


        val size = sharedPreference.getString("size","13.0f")
        binding.t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

        val weight = sharedPreference.getString("weight", "normal")
        when (weight) {
            "normal" -> binding.t1.setTypeface(null, Typeface.NORMAL) // "보통"
            "bold" -> binding.t1.setTypeface(null, Typeface.BOLD) // "굵게"
            "bolder" -> binding.t1.setTypeface(null, Typeface.BOLD_ITALIC) // "더 굵게"에 대한 대안
            else -> binding.t1.setTypeface(null, Typeface.NORMAL) // 기본값 처리
        }


        super.onResumeFragments()
    }


    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.item_board -> {
                Log.d("mobileapp", "예방접종 공유 게시판")
                val intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }


            R.id.vac_place -> {
                Log.d("mobileapp", "접종시설 한눈에 보기")
                val intent = Intent(this, GoogleMaps::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

            R.id.find_addr -> {
                Log.d("mobileapp", "시설 우편번호 찾기")
                val intent = Intent(this, DaumActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }


            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()

        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth() || MyApplication.email != null){
            button.text = "로그아웃"
            tv.text = "${MyApplication.email}님 \n 반갑습니다."
        }
        else{
            button.text="로그인"
            tv.text = "안녕하세요"
        }
    }


}
