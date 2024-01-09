package coding.faizal.ecommerce.di.profile

import coding.faizal.ecommerce.data.source.local.repository.address.AddressRepository
import coding.faizal.ecommerce.data.source.remote.repository.profile.ProfileRepository
import coding.faizal.ecommerce.domain.repository.address.IAddressRepository
import coding.faizal.ecommerce.domain.repository.profile.IProfileRepository
import coding.faizal.ecommerce.domain.usecase.address.AddressUseCase
import coding.faizal.ecommerce.domain.usecase.address.impl.AddressUseCaseImpl
import coding.faizal.ecommerce.domain.usecase.profile.ProfileUseCase
import coding.faizal.ecommerce.domain.usecase.profile.impl.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideProfileRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideProfileRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {



    @Binds
    @ProvideProfileRepositoryImpl
    abstract fun provideRepositoryImpl(profileUseCaseImpl: ProfileUseCaseImpl): ProfileUseCase

    @Binds
    @ProvideProfileRepository
    abstract fun provideRepository(profileRepository: ProfileRepository): IProfileRepository


}