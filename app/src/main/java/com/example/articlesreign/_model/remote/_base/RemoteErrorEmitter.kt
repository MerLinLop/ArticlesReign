package com.example.articlesreign._model.remote._base

/**
 * Created by eagskunst in 1/12/2019.
 */
interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}