package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

open class ConnectionsModel(
    @SerializedName("group-affiliation") val teamName : String? = null,
    @SerializedName("relatives") val relatives : String? = null
) : HeroModel()
