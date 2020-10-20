package com.example.articlesreign._model.remote

import com.example.articlesreign._model.local.articles.ArticlesEntity
import java.util.HashMap

class ArticlesResponseServer {
    var hits: List<ArticlesEntity>? = null
    var nbHits: Long? = null
    var page: Long? = null
    var nbPages: Long? = null
    var hitsPerPage: Long? = null
    var exhaustiveNbHits: Boolean? = null
    var query: String? = null
    var params: String? = null
    var processingTimeMS: Long? = null
    private val additionalProperties: MutableMap<String, Any> = HashMap()
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}