package com.example.articlesreign._model.repositories

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.articlesreign._model.local.AppDatabase
import com.example.articlesreign._model.local.articles.ArticlesEntity
import com.example.articlesreign._model.local.articlesremove.ArticlesRemoveEntity
import com.example.articlesreign.utils.ResponseObjetBasic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ArticlesRepository (app: Application): CoroutineScope {

    var mDatabase = AppDatabase.getInstance(app)
    var articlesDao = mDatabase.articlesDao()
    var articlesRemoveDao = mDatabase.articlesRemoveDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    @WorkerThread
    suspend fun saveAllArticle(listEventsRoom: List<ArticlesEntity>) = withContext(Dispatchers.IO){
        articlesDao.deleteArticles()
        articlesDao.saveAllArticle(listEventsRoom)
    }

    @WorkerThread
    suspend fun insertArticles(articles:ArticlesEntity) = withContext(Dispatchers.IO){
        articlesDao.insertArticle(articles)
    }
    @WorkerThread
    suspend fun getAllArticlesDB(responseObjetBasic: ResponseObjetBasic<List<ArticlesEntity>>)= withContext(Dispatchers.IO){
        val articles=articlesDao.getArticle()
        responseObjetBasic.onSuccess(articles)
    }
    @WorkerThread
    suspend fun deleteArticles(objectId: String) = withContext(Dispatchers.IO) {
        articlesDao.deleteArticle(objectId)
    }


    @WorkerThread
    suspend fun insertArticlesRemove(articles:ArticlesRemoveEntity) = withContext(Dispatchers.IO){
        if (articlesRemoveDao.chechArticleRemove(articles.objectID) == 0) {
            articlesRemoveDao.insertArticleRemove(articles)
        } else {

        }
    }
    @WorkerThread
    suspend fun getAllArticlesRemove(responseObjetBasic: ResponseObjetBasic<List<ArticlesRemoveEntity>>)= withContext(Dispatchers.IO){
        val articles=articlesRemoveDao.getArticleRemove()
        responseObjetBasic.onSuccess(articles)
    }
    @WorkerThread
    suspend fun deleteArticlesRemove(objectId: String) = withContext(Dispatchers.IO) {
        articlesRemoveDao.deleteArticleRemove(objectId)
    }
}
