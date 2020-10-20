package com.example.articlesreign._model.local.articles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.articlesreign._model.local.converters.ConverterListString
import com.example.articlesreign._model.local.converters.HighlightResultConverter
import java.util.*


@Entity(tableName = "_ARTICULO")
data class ArticlesEntity (

    @PrimaryKey
    @ColumnInfo(name = "objectID")
    var objectID: String = "",

    @ColumnInfo(name = "created_at")
    var created_at: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "author")
    var author: String? = null,
    @ColumnInfo(name = "points")
    var points: String? = null,
    @ColumnInfo(name = "story_text")
    var story_text: String? = null,
    @ColumnInfo(name = "comment_text")
    var comment_text: String? = null,
    @ColumnInfo(name = "num_comments")
    var num_comments: String? = null,
    @ColumnInfo(name = "story_id")
    var story_id: Long? = null,
    @ColumnInfo(name = "story_title")
    var story_title: String? = null,
    @ColumnInfo(name = "story_url")
    var story_url: String? = null,

    @ColumnInfo(name = "parent_id")
    var parent_id: Long? = null,
    @ColumnInfo(name = "created_at_i")
    var created_at_i: Int? = null

    /*@ColumnInfo(name = "_tags")
    @TypeConverters(ConverterListString::class)
    var _tags: List<String>? = null,*/

/*    @ColumnInfo(name = "_highlightResult")
    @TypeConverters(HighlightResultConverter::class)
    var _highlightResult: HighlightResultEntity? = null*/

)
    /*constructor()
    constructor(
        objectID: String,
        created_at: String?,
        title: String?,
        url: String?,
        author: String?,
        points: String?,
        story_text: String?,
        comment_text: String?,
        num_comments: String?,
        story_id: Long?,
        story_title: String?,
        story_url: String?,
        parent_id: Long?,
        created_at_i: Int?,
        _tags: List<String>?,
        _highlightResult: HighlightResultEntity?
    ) {
        this.objectID = objectID
        this.created_at = created_at
        this.title = title
        this.url = url
        this.author = author
        this.points = points
        this.story_text = story_text
        this.comment_text = comment_text
        this.num_comments = num_comments
        this.story_id = story_id
        this.story_title = story_title
        this.story_url = story_url
        this.parent_id = parent_id
        this.created_at_i = created_at_i
        this._tags = _tags
        this._highlightResult = _highlightResult
    }


}
*/