package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

open class PowerStatsModel(
    @SerializedName("intelligence") val intelligence : String? = null,
    @SerializedName("strength") val strengh : String? = null,
    @SerializedName("speed") val speed: String? = null,
    @SerializedName("durability") val durability: String? = null,
    @SerializedName("power") val power : String? = null,
    @SerializedName("combat") val combat : String? = null
) : HeroModel()
