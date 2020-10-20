package com.example.articlesreign._model.local.articles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {


    @Insert
    fun saveAllArticle(articleList: List<ArticlesEntity>)

    @Insert()
    fun insertArticle(article: ArticlesEntity)

    @Query("SELECT * FROM _ARTICULO")
    fun getArticle(): List<ArticlesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: ArticlesEntity)

    @Query("DELETE FROM _ARTICULO")
    fun deleteArticles()

    @Query("DELETE FROM _ARTICULO WHERE objectID = :object_ID")
    fun deleteArticle(object_ID: String)
}