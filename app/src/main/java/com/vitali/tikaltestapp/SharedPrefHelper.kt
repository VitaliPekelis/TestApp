package com.vitali.tikaltestapp

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(private val prefs : SharedPreferences)
{
    private val editor = prefs.edit()

    fun getSharedPrefInt(key:String, defValue:Int = 0):Int
    {
        return prefs.getInt(key,defValue)
    }

    fun setSharedPref(key:String, value:Int, saveAsynchronously:Boolean)
    {
        editor.putInt(key, value).also {
            if(saveAsynchronously)
            {
                it.apply()
            }
            else
            {
                it.commit()
            }
        }

    }


    companion object {
        fun create(context: Context):SharedPrefHelper
        {
            return SharedPrefHelper(context.getSharedPreferences("movies", 0))
        }
    }
}
