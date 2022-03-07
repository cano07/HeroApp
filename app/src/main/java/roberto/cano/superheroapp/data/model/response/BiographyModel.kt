package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

class BiographyModel(
    @SerializedName("full-name") val realName : String? = null,
    @SerializedName("alter-egos") val alterego : String? = null,
    @SerializedName("aliases") val otherNames: ArrayList<String>? = ArrayList(),
    @SerializedName("place-of-birth") val provenance: String? = null,
    @SerializedName("first-appearance") val comicDebut : String? = null,
    @SerializedName("publisher") val publisher : String? = null,
    @SerializedName("alignment") val alignment : String? = null
) : HeroModel()
