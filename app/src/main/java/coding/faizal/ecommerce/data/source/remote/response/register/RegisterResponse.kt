package coding.faizal.ecommerce.data.source.remote.response.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(

    @field:SerializedName("email")
    val email : String,

    @field:SerializedName("otp")
    val otp : String
):Parcelable
