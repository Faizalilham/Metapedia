package coding.faizal.ecommerce.core.domain.model.remote.authentication.login

data class User(
    val id : Int,
    val name : String,
    val email : String,
    val isAdmin : Boolean,
    val addresses : List<String>,
    val token : String
)
