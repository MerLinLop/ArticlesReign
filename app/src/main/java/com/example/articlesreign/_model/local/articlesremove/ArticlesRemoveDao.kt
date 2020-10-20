package com.example.articlesreign._model.local.articlesremove

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ArticlesRemoveDao {
    @Insert()
    fun insertArticleRemove(articleRemove: ArticlesRemoveEntity)

    @Query("SELECT * FROM _ARTICULO_ELIMINADO")
    fun getArticleRemove(): List<ArticlesRemoveEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticleRemove(ArticleRemove: ArticlesRemoveEntity)

    @Query("DELETE FROM _ARTICULO_ELIMINADO")
    fun deleteArticlesRemove()

    @Query("DELETE FROM _ARTICULO_ELIMINADO WHERE objectID = :object_ID")
    fun deleteArticleRemove(object_ID: String)

    @Query("SELECT COUNT(*) FROM _ARTICULO_ELIMINADO WHERE objectID = :objectId")
    fun chechArticleRemove(objectId: String?): Int
}