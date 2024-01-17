package coding.faizal.ecommerce.core.data.source.remote.response.product.payment

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: PaymentDataResponse
)

data class PaymentDataResponse(
    @field:SerializedName("token")
    val token: String,

)

data class PaymentInfoResponse(
    @field:SerializedName("token")
    val token: String,

)

data class PaymentResponseToken(

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("redirect_url")
    val redirectUrl: String
)
