package coding.faizal.ecommerce.data.source.remote.response.forgetPassword

data class NewPasswordRequest(
    val otp : Int,
    val newPassword : String
)
