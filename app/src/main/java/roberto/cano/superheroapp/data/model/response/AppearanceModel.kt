package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

open class AppearanceModel(
    @SerializedName("gender") val gender : String? = null ,
    @SerializedName("race") val race : String? = null ,
    @SerializedName("height") val height: ArrayList<String>? = ArrayList() ,
    @SerializedName("weight") val weight: ArrayList<String>? = ArrayList(),
    @SerializedName("eye-color") val eyesColor : String? = null,
    @SerializedName("hair-color") val hairColor : String? = null
) : HeroModel()
