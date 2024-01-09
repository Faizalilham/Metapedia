package coding.faizal.ecommerce.data.source.remote.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponseAfterVerification(

    @field:SerializedName("id")
    val id : Int,

    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("email")
    val email : String,


    @field:SerializedName("isAdmin")
    val isAdmin : Boolean,

    @field:SerializedName("addresses")
    val address : List<String>,

    @field:SerializedName("token")
    val token : String
)


data class ResponseDataUser<T>(
    val user: T
)
