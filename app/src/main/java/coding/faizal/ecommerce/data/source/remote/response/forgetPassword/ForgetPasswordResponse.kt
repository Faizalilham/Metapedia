package coding.faizal.ecommerce.data.source.remote.response.forgetPassword

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
