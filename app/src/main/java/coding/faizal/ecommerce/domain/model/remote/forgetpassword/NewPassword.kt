package coding.faizal.ecommerce.domain.model.remote.forgetpassword

data class NewPassword(
    val id : String,
    val name : String,
    val email : String,
    val isAdmin : Boolean,
    val addresses : List<String>,
)
