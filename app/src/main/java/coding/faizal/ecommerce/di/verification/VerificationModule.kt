package coding.faizal.ecommerce.di.verification


import coding.faizal.ecommerce.data.source.remote.repository.verification.VerificationRepository
import coding.faizal.ecommerce.domain.repository.verification.IVerificationRepository
import coding.faizal.ecommerce.domain.usecase.verification.VerificationUseCase
import coding.faizal.ecommerce.domain.usecase.verification.impl.VerificationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideVerificationRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideVerificationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class VerificationModule {



    @Binds
    @ProvideVerificationRepositoryImpl
    abstract fun provideRepositoryImpl(VerificationUseCaseImpl : VerificationUseCaseImpl): VerificationUseCase

    @Binds
    @ProvideVerificationRepository
    abstract fun provideRepository(VerificationRepository: VerificationRepository): IVerificationRepository


}