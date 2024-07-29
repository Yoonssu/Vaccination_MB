package com.example.vaccination

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import com.example.vaccination.databinding.ActivityDaumBinding

class DaumActivity : AppCompatActivity() {

    private lateinit var daumWebView: WebView
    private lateinit var daumResult: TextView
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDaumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daumResult = binding.daumResult

        // WebView 초기화
        initWebView()

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = Handler()
    }

    private fun initWebView() {
        // WebView 설정
        daumWebView = findViewById(R.id.daum_webview)

        // JavaScript 허용
        daumWebView.settings.javaScriptEnabled = true

        // JavaScript의 window.open 허용
        daumWebView.settings.javaScriptCanOpenWindowsAutomatically = true

        // JavaScript이벤트에 대응할 함수를 정의한 클래스를 붙여줌
        daumWebView.addJavascriptInterface(AndroidBridge(), "TestApp")

        // Web client를 Chrome으로 설정
        daumWebView.webChromeClient = WebChromeClient()

        // WebView에 HTML 파일 주소 로드
        daumWebView.loadUrl("file:///android_asset/html/daum.html")
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            handler.post {
                daumResult.text = String.format("(%s) %s %s", arg1, arg2, arg3)
            }
        }
    }
}
