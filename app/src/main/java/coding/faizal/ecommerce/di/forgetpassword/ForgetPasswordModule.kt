package coding.faizal.ecommerce.di.forgetpassword

import coding.faizal.ecommerce.data.source.remote.repository.forgetpassword.ForgetPasswordRepository
import coding.faizal.ecommerce.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.domain.usecase.forgetPassword.ForgetPasswordUseCase
import coding.faizal.ecommerce.domain.usecase.forgetPassword.impl.ForgetPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideForgetPasswordRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideForgetPasswordRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ForgetPasswordModule {



    @Binds
    @ProvideForgetPasswordRepositoryImpl
    abstract fun provideRepositoryImpl(ForgetPasswordUseCaseImpl : ForgetPasswordUseCaseImpl): ForgetPasswordUseCase

    @Binds
    @ProvideForgetPasswordRepository
    abstract fun provideRepository(ForgetPasswordRepository: ForgetPasswordRepository): IForgetPasswordRepository


}