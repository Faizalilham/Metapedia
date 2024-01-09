package coding.faizal.ecommerce.data.source.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
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
    val address : List<String>,

    @field:SerializedName("token")
    val token : String
)
