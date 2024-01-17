package coding.faizal.ecommerce.core.data.source.remote.response


import coding.faizal.ecommerce.preferences.AuthPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: AuthPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = runBlocking {
            withContext(Dispatchers.IO) {
                pref.getToken().first()
            }
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}
