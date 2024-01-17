package coding.faizal.ecommerce.core.data.source.remote.response.authentication.register

data class RegisterRequestAfterVerification(
    val email : String,
    val name : String,
    val password : String
)