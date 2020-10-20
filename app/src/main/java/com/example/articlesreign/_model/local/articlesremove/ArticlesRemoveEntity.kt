package com.example.articlesreign._model.local.articlesremove

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "_ARTICULO_ELIMINADO")
data class ArticlesRemoveEntity (
    @PrimaryKey
    @ColumnInfo(name = "objectID")
    var objectID: String

)

