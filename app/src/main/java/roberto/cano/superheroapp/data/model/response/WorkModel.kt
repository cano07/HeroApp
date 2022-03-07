package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

class WorkModel(
    @SerializedName("occupation") val title : String? = null,
    @SerializedName("base") val base : String? = null
) : HeroModel()
