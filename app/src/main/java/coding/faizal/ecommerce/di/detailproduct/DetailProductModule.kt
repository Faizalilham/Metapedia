package coding.faizal.ecommerce.di.detailproduct

import coding.faizal.ecommerce.data.source.local.repository.detailproduct.DetailProductRepository
import coding.faizal.ecommerce.domain.repository.detailproduct.IDetailProductRepository
import coding.faizal.ecommerce.domain.usecase.detailproduct.DetailProductUseCase
import coding.faizal.ecommerce.domain.usecase.detailproduct.impl.DetailProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideDetailProductRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideDetailProductRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailProductModule {



    @Binds
    @ProvideDetailProductRepositoryImpl
    abstract fun provideRepositoryImpl(detailProductUseCaseImpl : DetailProductUseCaseImpl): DetailProductUseCase

    @Binds
    @ProvideDetailProductRepository
    abstract fun provideRepository(detailRepository: DetailProductRepository): IDetailProductRepository


}