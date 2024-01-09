package coding.faizal.ecommerce.di.address

import coding.faizal.ecommerce.data.source.local.repository.address.AddressRepository
import coding.faizal.ecommerce.domain.repository.address.IAddressRepository
import coding.faizal.ecommerce.domain.usecase.address.AddressUseCase
import coding.faizal.ecommerce.domain.usecase.address.impl.AddressUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideAddressRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideAddressRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AddressModule {



    @Binds
    @ProvideAddressRepositoryImpl
    abstract fun provideRepositoryImpl(addressUseCaseImpl : AddressUseCaseImpl): AddressUseCase

    @Binds
    @ProvideAddressRepository
    abstract fun provideRepository(addressRepository: AddressRepository): IAddressRepository


}