package coding.faizal.ecommerce.data.source.remote.response.register

data class RegisterRequestAfterVerification(
    val email : String,
    val name : String,
    val password : String
)