package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

open class HeroModel(
    @SerializedName("id") val id : String? = null ,
    @SerializedName("name") var name : String? = null ,
    @SerializedName("response") val response : String? = null ,
)
