package com.example.handybook

import android.content.Context
import android.content.SharedPreferences
import com.example.handybook.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyShared private constructor(context: Context){
    private val shared: SharedPreferences = context.getSharedPreferences("data", 0)
    private val edit: SharedPreferences.Editor = shared.edit()
    private val gson  = Gson()

    companion object{
        private var instance :MyShared? = null
        fun getInstance(context: Context):MyShared{
            if (instance == null){
                instance = MyShared(context)
            }
            return instance!!
        }
    }

    fun setUser(user: User){
        val data  = gson.toJson(user)
        edit.putString("user", data).apply()
    }

    fun logOut(){
        edit.putString("user", "").apply()
    }
    fun getUser():User?{
        val data = shared.getString("user", "")
        if (data == "") return null
        val typeToken = object : TypeToken<User>() {}.type
        return gson.fromJson(data, typeToken)
    }


}