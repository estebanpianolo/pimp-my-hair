package com.etienne.pimpmyhair

import android.os.Bundle
import com.etienne.libraries.archi.view.RelaunchingActivity

class MainActivity : RelaunchingActivity() {

    companion object {
        var isInitialized = false
    }

    init {
        isInitialized = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
