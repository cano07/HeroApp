package roberto.cano.superheroapp.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    var retrofit : Retrofit? = null


    fun getAPIService(): Retrofit {

        retrofit?.let {
            return it
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl( Constants.URL_BASE + "/" + Constants.API_KEY + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit as Retrofit

    }
}