package coding.faizal.ecommerce.data.source.remote.response



data class ListResponse<T>(
    val code : Int,
    val status : String,
    val success : Boolean,
    val message : String,
    val data : List<T>
)
