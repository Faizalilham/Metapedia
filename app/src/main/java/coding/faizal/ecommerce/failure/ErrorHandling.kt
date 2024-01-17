package coding.faizal.ecommerce.failure

import android.util.Log
import coding.faizal.ecommerce.core.data.Resource
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException

class ErrorHandling<T>(private val flowCollector: FlowCollector<Resource<T>>) {

    suspend fun handleError(code: String, message: String) {
        val errorMessage = when (code) {
            "400" -> {
                Log.d("ERROR BODY", "Bad Request: $message")
                message
            }
            else -> message
        }
        emitError(errorMessage)
    }

    suspend fun handleHttpException(e: HttpException) {
        val errorBodyString = e.response()?.errorBody()?.string()
        Log.d("ERROR BODY", "Bad Request: $errorBodyString")

        try {
            val jsonElement = JsonParser.parseString(errorBodyString)
            val message = jsonElement.asJsonObject.get("message").asString

            emitError(message)
        } catch (jsonException: Exception) {
            emitError(jsonException.message)
        }
    }

    private suspend fun emitError(errorMessage: String?) {
        flowCollector.emit(coding.faizal.ecommerce.core.data.Resource.Error(message = errorMessage))
    }
}

