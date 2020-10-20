package com.example.articlesreign.utils

interface ResponseObjetBasic<T> {

    fun onSuccess(entity: T)

    fun onError(message: String)

}