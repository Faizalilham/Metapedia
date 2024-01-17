package coding.faizal.ecommerce.core.data.source.remote.response

data class SingleResponse<T>(
    val code : String,
    val status : String,
    val success : Boolean,
    val message : String,
    val data : T
)
