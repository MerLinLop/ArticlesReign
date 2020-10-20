package com.example.articlesreign._view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import com.example.appml._view.base.BasicMethods
import com.example.articlesreign.R
import kotlinx.android.synthetic.main.activity_detail_article.*

class DetailArticleActivity : AppCompatActivity(), BasicMethods {

    lateinit var ajustesVisorWeb : WebSettings
    lateinit var direccionWeb : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        initObservables()
        init()
        initListeners()


    }

    override fun initObservables() {

    }

    override fun init() {
        direccionWeb = intent.extras?.getString("website").toString()
        ajustesVisorWeb = webViewDetail.settings
        ajustesVisorWeb.javaScriptEnabled = true
        webViewDetail.loadUrl(direccionWeb)
    }

    override fun initListeners() {
        imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }
}