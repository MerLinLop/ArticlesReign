package com.example.articlesreign._viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.articlesreign._model.local.articles.ArticlesEntity
import com.example.articlesreign._model.local.articlesremove.ArticlesRemoveEntity
import com.example.articlesreign._model.remote._base.OnResponse
import com.example.articlesreign._model.repositories.ArticlesRepository
import com.example.articlesreign._model.repositories.backend.ArticlesBackend
import com.example.articlesreign.utils.ResponseObjetBasic
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel (application: Application): BaseViewModel(application), CoroutineScope {

    private val TAG: String = MainViewModel::class.java.simpleName
    private val job: Job = SupervisorJob()
    private var articles_repository = ArticlesRepository(application)
    var mArticlesBackend = ArticlesBackend(application)

    private val _listArticles = MutableLiveData<List<ArticlesEntity>>()
    val listArticles : LiveData<List<ArticlesEntity>> get() = _listArticles

  //  private val _listArticles = MutableLiveData<List<ArticlesEntity>>()
//    val listArticles : LiveData<List<ArticlesEntity>> get() = _listArticles


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // By default child coroutines will run on the main thread.


    suspend fun getAllArticlesApi(){

        viewModelScope.launch {
            mArticlesBackend.syncArticles(
                object : OnResponse<ArticlesEntity> {
                    override fun onResponse(
                        responseType: OnResponse.ResponseType,
                        entity: ArticlesEntity?,
                        listEntity: List<ArticlesEntity>?
                    ) {
                        if(!listEntity.isNullOrEmpty()){
                            //GUARDA LOS ARTICULOS EN LA BASE DE DATOS
                            insertAllArticle(listEntity)
                            filterArticlesRemove(listEntity)
                        }
                    }

                    override fun onError(code: Int, error: String?) {
                        Log.e(TAG,"ERROR TRAER Articulos $error")
                        val listEntity: List<ArticlesEntity> = arrayListOf()
                        _listArticles.postValue(listEntity)
                    }
                })
        }
    }

    fun filterArticlesRemove(listEntity: List<ArticlesEntity>){
        //TRAE TODOS LOS ARTICULOS QUE FUERON ELIIMINADOS
        getAllArticlesDelete(object : ResponseObjetBasic<List<ArticlesRemoveEntity>> {
            override fun onSuccess(listids: List<ArticlesRemoveEntity>) {
                val listId : MutableList<String> = mutableListOf()
                val listNewFilter : MutableList<ArticlesEntity> = mutableListOf()
                //SI HAY ARTICULOS ELIMINADOS LOS SACA DE LA VISTA
                if(!listids.isNullOrEmpty()){
                    for (id in listids){
                        listId.add(id.objectID)
                    }
                    for (article in listEntity){
                        if(!listId.contains(article.objectID)){
                            listNewFilter.add(article)
                        }
                    }
                    _listArticles.postValue(listNewFilter)
                }
                else{
                    _listArticles.postValue(listEntity)
                }
            }
            override fun onError(message: String) {
                _listArticles.postValue(listEntity)
            }
        })
    }

    fun saveArticleDetele(article: ArticlesEntity){
        val articleDelete = ArticlesRemoveEntity(article.objectID)

        launch {
            articles_repository.insertArticlesRemove(articleDelete)
        }
    }


    fun insertAllArticle(listEntity: List<ArticlesEntity>) {
        launch {
            articles_repository.saveAllArticle(listEntity)
        }
    }

    fun getAllArticlesDB(){
        getAllArticles(object : ResponseObjetBasic<List<ArticlesEntity>> {
            override fun onSuccess(entity: List<ArticlesEntity>) {
                filterArticlesRemove(entity)
                //_listArticles.postValue(entity)
            }

            override fun onError(message: String) {
                Log.e(TAG,"ERROR TRAER Articulos $error")
                val listEntity: List<ArticlesEntity> = arrayListOf()
                _listArticles.postValue(listEntity)
            }
        })

    }

    fun getAllArticles(responseObjetBasic: ResponseObjetBasic<List<ArticlesEntity>>) {
        launch {
            articles_repository.getAllArticlesDB(responseObjetBasic)
        }
    }

    fun getAllArticlesDelete(responseObjetBasic: ResponseObjetBasic<List<ArticlesRemoveEntity>>) {
        launch {
            articles_repository.getAllArticlesRemove(responseObjetBasic)
        }
    }

}