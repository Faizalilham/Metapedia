package coding.faizal.ecommerce.di

import android.content.Context
import coding.faizal.ecommerce.preferences.AuthPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) : AuthPreferences{
        return AuthPreferences(context)
    }
}