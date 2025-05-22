package com.agarcia.myfirstandroidapp

import android.app.Application
import com.agarcia.myfirstandroidapp.data.AppProvider

class MyFirstAndroidAppAplication:Application() {
    val appProvider by lazy {
        AppProvider(this)
    }
}