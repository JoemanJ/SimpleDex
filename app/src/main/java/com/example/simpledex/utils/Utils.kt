package com.example.simpledex.utils

import android.content.Context
import android.widget.Toast

class Utils {
    companion object{
        fun prinToast(msg: String, context: Context, length: Int = Toast.LENGTH_LONG){
            Toast.makeText(context, msg, length).show()
        }
    }
}