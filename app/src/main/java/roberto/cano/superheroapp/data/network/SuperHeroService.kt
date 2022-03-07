 package roberto.cano.superheroapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import roberto.cano.superheroapp.data.model.response.BiographyModel
import roberto.cano.superheroapp.data.model.response.SearchHeroesRes

interface SuperHeroService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("search/{letter}")
    suspend fun searchHeroes(@Path("letter") letter: Char): Response<SearchHeroesRes>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/powerstats")
    suspend fun getPowerStats(@Path("id") id : String ): Response< BiographyModel >

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/biography")
    suspend fun getBiography(@Path("id") id : String ): Response< BiographyModel >

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/image")
    suspend fun getImage(@Path("id") id : String ): Response< BiographyModel >

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/work")
    suspend fun getWork(@Path("id") id : String ): Response< BiographyModel >

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/connections")
    suspend fun getConnections(@Path("id") id : String ): Response< BiographyModel >

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("{id}/appearance")
    suspend fun getAppearance(@Path("id") id : String ): Response< BiographyModel >
}
