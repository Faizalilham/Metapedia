package coding.faizal.ecommerce.core.data.source.remote.response.authentication.praregister

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
