package com.example.articlesreign._view

import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articlesreign.R
import com.example.articlesreign._model.local.articles.ArticlesEntity
import com.example.articlesreign._view.adapters.ArticlesRecyclerviewAdapter
import com.example.articlesreign._viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.Comparator
import com.example.appml._view.base.BasicMethods as BasicMethods1


class MainActivity : AppCompatActivity() , BasicMethods1 {

    lateinit var mMainViewModel: MainViewModel
    lateinit var adapter: ArticlesRecyclerviewAdapter
    protected val TAG = this.javaClass.simpleName
    private val p = Paint()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }


    override fun init() {

        if (!checkInternetConexion()) {
            mMainViewModel.getAllArticlesDB()
        }
        else{
            getAllArticles()
        }

        mMainViewModel.listArticles.observe(this,
            androidx.lifecycle.Observer { listArticles: List<ArticlesEntity> ->
                val compareById = Comparator{ o1: ArticlesEntity, o2: ArticlesEntity ->
                    o2.created_at!!.compareTo(o1.created_at!!)
                }
                Collections.sort(listArticles, compareById)
                swipeLayoutRefresh.isRefreshing = false
                recyclerViewArticles.layoutManager = LinearLayoutManager(this)
                recyclerViewArticles.setHasFixedSize(true)
                adapter = ArticlesRecyclerviewAdapter(this, listArticles as MutableList<ArticlesEntity>)

                recyclerViewArticles.adapter = adapter

                adapter.setListner(object :
                    ArticlesRecyclerviewAdapter.ArticlesRecyclerviewAdapterListener {
                    override fun onClick(product: ArticlesEntity) {

                        getWeb(product)

                    }

                    override fun onDeleteItem(articles: ArticlesEntity, position: Int) {
                        mMainViewModel.saveArticleDetele(articles)
                        adapter.remove(position)

                    }

                })
            })
    }



    private fun getAllArticles() {

        swipeLayoutRefresh.isRefreshing=true
        lifecycleScope.launch {
            mMainViewModel.getAllArticlesApi()
        }

    }
    private fun getWeb(product: ArticlesEntity) {


        val nIntent =
            Intent(this, DetailArticleActivity::class.java)
        nIntent.putExtra("website", product.story_url)
        startActivity(nIntent)

    }

    override fun initListeners() {

        swipeLayoutRefresh.setOnRefreshListener {
            if (!checkInternetConexion()) {
                mMainViewModel.getAllArticlesDB()
            }
            else{
                getAllArticles()
            }

        }
    }

    fun checkInternetConexion(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        try {
            val cm =
              getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName
                        .equals("WIFI", ignoreCase = true)
                ) if (ni.isConnected) haveConnectedWifi = true
                if (ni.typeName
                        .equals("MOBILE", ignoreCase = true)
                ) if (ni.isConnected) haveConnectedMobile = true
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
        return haveConnectedWifi || haveConnectedMobile
    }

}