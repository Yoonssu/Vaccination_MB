package com.example.vaccination

data class ItemData (



    var docId: String? = null, //밑에 4개의 값 위에 있음, 밑에 4개와 한 번에 받아올 수 없음, 스토리지 아이디

    //값을 가지고 와서 리사이클러 뷰에 넣음
    var email: String? = null,
    var stars: Float = 0.0f,
    var comments: String? = null,
    var date_time: String? = null
)