package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

class SearchHeroesRes(
    @SerializedName("response") val response: String? = null,
    @SerializedName("results-for") val resultsFor: String? = null,
    @SerializedName("results") val results: ArrayList<HeroResultResponse>? = ArrayList()
)

class HeroResultResponse(
    @SerializedName("powerstats") val powerStats : PowerStatsModel? = null,
    @SerializedName("biography") val biography : BiographyModel? = null,
    @SerializedName("appearance") val appereance : AppearanceModel? = null,
    @SerializedName("work") val work : WorkModel? = null,
    @SerializedName("connections") val connections : ConnectionsModel? = null,
    @SerializedName("image") val image : ImageModel? = null
): HeroModel ()
