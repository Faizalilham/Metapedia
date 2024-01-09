package coding.faizal.ecommerce.di.product


import coding.faizal.ecommerce.data.source.remote.repository.product.ProductRepository
import coding.faizal.ecommerce.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.domain.usecase.product.ProductUseCase
import coding.faizal.ecommerce.domain.usecase.product.impl.ProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideProductRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideProductRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {



    @Binds
    @ProvideProductRepositoryImpl
    abstract fun provideRepositoryImpl(productUseCaseImpl : ProductUseCaseImpl): ProductUseCase

    @Binds
    @ProvideProductRepository
    abstract fun provideRepository(productRepository: ProductRepository): IProductRepository


}