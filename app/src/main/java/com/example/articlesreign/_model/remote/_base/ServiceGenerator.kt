package com.example.articlesreign._model.remote._base

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceGenerator {

    fun <S> createService(
        baseUrl: String,
        basicSecurityHeader: String?,
        basicSecurityValue: String?,
        serviceClass: Class<S>
    ): S { //region example convert date time
//basic date format
//Gson gson = new GsonBuilder().setDateFormat(MessageServices.DEFAULT_FORMAT_DATETIME).setLenient().create();
        val gson = GsonBuilder()
            .registerTypeAdapter(
                Date::class.java,
                GsonSerializers.serializerDateToTimeLong
            ) //.registerTypeAdapter(Date.class, GsonSerializers.deserializerDateToTimeLong)
            .create()
        //endregion
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                getUnsafeClientBuilder(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                    , 10,
                    basicSecurityHeader,
                    basicSecurityValue
                )
            )
            .build()
        return retrofit.create(serviceClass)
    }

    fun getUnsafeClientBuilder(
        interceptor: HttpLoggingInterceptor?,
        timeout: Int,
        basicSecurityHeader: String?,
        basicSecurityValue: String?
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return try {
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val clientBuilder = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(9999, TimeUnit.SECONDS)
                .writeTimeout(9999, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier { hostname: String?, session: SSLSession? -> true }
            addBasicSecurity(
                clientBuilder,
                basicSecurityHeader,
                basicSecurityValue
            )
            clientBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
    private fun addBasicSecurity(
        clientBuilder: OkHttpClient.Builder,
        basicSecurityHeader: String?,
        basicSecurityValue: String?
    ): OkHttpClient.Builder {
        if (basicSecurityHeader != null && !basicSecurityHeader.isEmpty()) {
            clientBuilder.addInterceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader(
                        basicSecurityValue,
                        basicSecurityValue
                    ) //.addHeader("deviceplatform", "android")
//.removeHeader("User-Agent")
//.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                    .build()
                val response = chain.proceed(request)
                response
            }
        }
        return clientBuilder
    }
}
