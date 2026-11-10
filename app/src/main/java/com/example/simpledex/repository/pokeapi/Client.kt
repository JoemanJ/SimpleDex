package com.example.simpledex.repository.pokeapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object {
        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder().build()
        }

        private val retrofitInstances = mutableMapOf<String, Retrofit>()

        private fun getRetrofitInstance(baseUrl: String): Retrofit {
            return retrofitInstances.getOrPut(baseUrl) {
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

        fun <S> createService(className: Class<S>, baseUrl: String): S{
            return getRetrofitInstance(baseUrl).create(className)
        }
    }
}