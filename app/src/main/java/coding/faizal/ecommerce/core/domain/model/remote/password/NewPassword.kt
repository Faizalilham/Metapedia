package coding.faizal.ecommerce.core.domain.model.remote.password

data class NewPassword(
    val id : String,
    val name : String,
    val email : String,
    val isAdmin : Boolean,
    val addresses : List<String>,
)
