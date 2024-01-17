package coding.faizal.ecommerce.core.data

sealed class Resource<T>(val data : T? = null,val message : String? = null) {
    class Loading<T>(data : T? = null) : coding.faizal.ecommerce.core.data.Resource<T>(data = data)
    class Success<T>(data : T,message : String): coding.faizal.ecommerce.core.data.Resource<T>(data = data,message = message)
    class Error<T>(message : String?,data : T? = null):
        coding.faizal.ecommerce.core.data.Resource<T>(data = data, message = message)
}