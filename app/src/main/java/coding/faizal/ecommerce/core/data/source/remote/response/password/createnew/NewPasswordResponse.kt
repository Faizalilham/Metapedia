package coding.faizal.ecommerce.core.data.source.remote.response.password.createnew

import com.google.gson.annotations.SerializedName

data class NewPasswordResponse(
   @field:SerializedName("_id")
   val id : String,

   @field:SerializedName("name")
   val name : String,

   @field:SerializedName("email")
   val email : String,

   @field:SerializedName("isAdmin")
   val isAdmin : Boolean,

   @field:SerializedName("addresses")
   val addresses : List<String>
)
