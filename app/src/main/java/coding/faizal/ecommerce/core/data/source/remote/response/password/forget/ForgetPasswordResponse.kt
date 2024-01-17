package coding.faizal.ecommerce.core.data.source.remote.response.password.forget

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForgetPasswordResponse(

    @field:SerializedName("email")
    val email : String,

    @field:SerializedName("otp")
    val otp : String

): Parcelable
