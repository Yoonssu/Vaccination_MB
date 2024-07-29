package com.example.vaccination

data class HinfoData(val Age:Int?, val name: String?, val addr: String?)

data class PhpResponse(val result : ArrayList<HinfoData>)