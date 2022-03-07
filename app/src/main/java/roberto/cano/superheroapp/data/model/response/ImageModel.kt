package roberto.cano.superheroapp.data.model.response

import com.google.gson.annotations.SerializedName

open class ImageModel(
    @SerializedName("url") val imageUrl : String? = null
)
