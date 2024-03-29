package coding.faizal.ecommerce.core.data.source.remote.response.password.reset

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @field:SerializedName("_id")
    val id : String,

    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("email")
    val email : String,

    @field:SerializedName("image")
    val image : String,

    @field:SerializedName("isAdmin")
    val isAdmin : Boolean,

    @field:SerializedName("addresses")
    val addresses : List<String>
)
