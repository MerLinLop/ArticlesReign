package com.example.articlesreign._model.local.articles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "_ARTICULO_RAPIDO")
class HighlightResultEntity {

  /*  @PrimaryKey
    @ColumnInfo(name = "ID")
    var id: Int? = null*/

    @ColumnInfo(name = "author")
    var author: Any? = null
    @ColumnInfo(name = "comment_text")
    var comment_text: Any? = null
    @ColumnInfo(name = "story_title")
    var story_title: Any? = null
    @ColumnInfo(name = "story_url")
    var story_url: Any? = null

    constructor()

    constructor(author: Any?, comment_text: Any?, story_title: Any?, story_url: Any?) {
        this.author = author
        this.comment_text = comment_text
        this.story_title = story_title
        this.story_url = story_url
    }
}
