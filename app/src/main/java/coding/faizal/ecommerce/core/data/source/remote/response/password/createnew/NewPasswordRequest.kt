package coding.faizal.ecommerce.core.data.source.remote.response.password.createnew

data class NewPasswordRequest(
    val otp : Int,
    val newPassword : String
)
