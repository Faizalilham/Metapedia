package coding.faizal.ecommerce.di.user

import coding.faizal.ecommerce.data.source.remote.repository.user.UserRepository
import coding.faizal.ecommerce.domain.repository.user.IUserRepository
import coding.faizal.ecommerce.domain.usecase.user.UserUseCase
import coding.faizal.ecommerce.domain.usecase.user.impl.UserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideUserRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideUserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {



    @Binds
    @ProvideUserRepositoryImpl
    abstract fun provideRepositoryImpl(profileUseCaseImpl: UserUseCaseImpl): UserUseCase

    @Binds
    @ProvideUserRepository
    abstract fun provideRepository(profileRepository: UserRepository): IUserRepository


}