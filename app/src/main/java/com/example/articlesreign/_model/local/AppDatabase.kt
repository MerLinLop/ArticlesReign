package com.example.articlesreign._model.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.articlesreign._model.local.articles.ArticlesDao
import com.example.articlesreign._model.local.articles.ArticlesEntity
import com.example.articlesreign._model.local.articlesremove.ArticlesRemoveDao
import com.example.articlesreign._model.local.articlesremove.ArticlesRemoveEntity
//
@Database(
    entities = [
        ArticlesEntity::class,
        ArticlesRemoveEntity::class
    ], version = 2)

abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
    abstract fun articlesRemoveDao(): ArticlesRemoveDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also {
                        INSTANCE = it
                    }
            }

        private fun buildDatabase(context: Context) =
            databaseBuilder(
                context,
                AppDatabase::class.java, "articlesreign.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("ONCREATE", "Database has been created.")

                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("ONOPEN", "Database has been opened.")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }
}