package com.luuu.loadingview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            loading_view.startAnimator()
        }

        btn_stop.setOnClickListener {
            loading_view.stopAnimator()
        }
    }
}
