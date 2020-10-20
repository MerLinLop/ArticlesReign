package com.example.articlesreign._model.repositories.backend

import android.app.Application
import android.util.Log
import com.example.articlesreign._model.local.articles.ArticlesEntity
import com.example.articlesreign._model.networking.ArticlesApi
import com.example.articlesreign._model.remote.ArticlesResponseServer
import com.example.articlesreign._model.remote._base.ErrorType
import com.example.articlesreign._model.remote._base.OnResponse
import com.example.articlesreign._model.remote._base.RemoteErrorEmitter
import com.example.articlesreign._model.remote._base.ServiceGenerator
import com.example.articlesreign._model.repositories._base.BaseRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ArticlesBackend(application: Application):  CoroutineScope, BaseRemoteRepository()  {

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var apiService: ArticlesApi = ServiceGenerator.createService(
        BASE_URL, null, null, ArticlesApi::class.java
    )

    companion object{
        val TAG=ArticlesBackend.javaClass.simpleName
    }

    ///API GET PRODUCTOS///////////////////
    suspend fun getArticlesApi(
        remoteErrorEmitter: RemoteErrorEmitter
    ): ArticlesResponseServer? {
        return safeApiCall(remoteErrorEmitter) {
            apiService.getArticlesApi()
        }
    }
    ///GET PRODUCTOS COINCIDAN CON LA BUSQUEDA///////////////////
    suspend fun syncArticles( onResponse: OnResponse<ArticlesEntity>) {
        var auxLisr: MutableList<ArticlesEntity> = mutableListOf()
        getArticlesApi( object : RemoteErrorEmitter {
            override fun onError(msg: String) {
                Log.e(TAG, "error:  $msg")
                onResponse.onError(0, msg)
            }
            override fun onError(errorType: ErrorType) {
                Log.e(TAG, "error:  $errorType.name")
                onResponse.onError(0, errorType.name)
            }
        })?.let { list ->
            Log.d(TAG, "BUSQUEDA ARTICULOS:  $list")
            if(!list.hits.isNullOrEmpty()){
                for(i in list.hits!!){
                    auxLisr.add(i)
                }
            }

        }
        onResponse.onResponse(OnResponse.ResponseType.OK, null, auxLisr)
    }

}