package com.irfanirawansukirman.mvvmplayground

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.logD(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun AppCompatActivity.logE(message: String) {
    Log.e(this::class.java.simpleName, message)
}

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}