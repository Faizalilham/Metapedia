package coding.faizal.ecommerce.data.source.remote.response

data class SingleResponse<T>(
    val code : String,
    val status : String,
    val success : Boolean,
    val message : String,
    val data : T
)
