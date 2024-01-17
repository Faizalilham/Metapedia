package coding.faizal.ecommerce.di


import coding.faizal.ecommerce.core.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.core.data.source.remote.network.product.ApiProductService
import coding.faizal.ecommerce.core.data.source.remote.response.RequestInterceptor
import coding.faizal.ecommerce.preferences.AuthPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun httpLogging():HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideRequestInterceptor(prefs: AuthPreferences) : RequestInterceptor {
        return RequestInterceptor(prefs)
    }


    @Provides
    @Singleton
    fun okHttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor,requestInterceptor: RequestInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLoggingInterceptor)
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://sipendo.simagang.my.id/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun apiService(retrofit : Retrofit): ApiAuthService {
       return  retrofit.create(ApiAuthService::class.java)
    }

    @Provides
    fun apiProductService(retrofit : Retrofit): ApiProductService {
        return  retrofit.create(ApiProductService::class.java)
    }


}