package com.example.vaccination

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

//Dex : Dalvic Executable (64k)
class MyApplication : MultiDexApplication() {
    companion object{
        lateinit var auth : FirebaseAuth
        var email:String? = null

        lateinit var db : FirebaseFirestore
        lateinit var storage: FirebaseStorage



        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            if(currentUser != null){
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }
    }
    override fun onCreate(){
        super.onCreate()

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance() //db 초기화, firestore에 데이터 저장하고,
        storage = Firebase.storage
    }
}