package coding.faizal.ecommerce.core.data.source.remote.response.verification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class VerificationResponse(

    @field:SerializedName("token")
    val token : String,

    @field:SerializedName("otpCreatedAt")
    val otpCreatedAt : String

):Parcelable
