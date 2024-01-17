package coding.faizal.ecommerce.di.authentification

import coding.faizal.ecommerce.data.source.remote.repository.authentication.AuthenticationRepository
import coding.faizal.ecommerce.domain.repository.authentication.IAuthenticationRepository
import coding.faizal.ecommerce.domain.usecase.authentication.AuthenticationUseCase
import coding.faizal.ecommerce.domain.usecase.authentication.impl.AuthenticationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideAuthenticationRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideAuthenticationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {



    @Binds
    @ProvideAuthenticationRepositoryImpl
    abstract fun provideRepositoryImpl(authenticationUseCaseImpl : AuthenticationUseCaseImpl): AuthenticationUseCase

    @Binds
    @ProvideAuthenticationRepository
    abstract fun provideRepository(authenticationRepository: AuthenticationRepository): IAuthenticationRepository


}