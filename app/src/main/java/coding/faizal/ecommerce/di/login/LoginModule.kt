package coding.faizal.ecommerce.di.login

import coding.faizal.ecommerce.data.source.remote.repository.login.LoginRepository
import coding.faizal.ecommerce.domain.repository.login.ILoginRepository
import coding.faizal.ecommerce.domain.usecase.login.LoginUseCase
import coding.faizal.ecommerce.domain.usecase.login.impl.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideLoginRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideLoginRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {



    @Binds
    @ProvideLoginRepositoryImpl
    abstract fun provideRepositoryImpl(loginUseCaseImpl : LoginUseCaseImpl): LoginUseCase

    @Binds
    @ProvideLoginRepository
    abstract fun provideRepository(loginRepository: LoginRepository): ILoginRepository


}