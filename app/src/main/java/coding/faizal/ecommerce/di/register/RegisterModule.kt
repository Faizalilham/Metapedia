package coding.faizal.ecommerce.di.register

import coding.faizal.ecommerce.data.source.local.repository.help.HelpRepository
import coding.faizal.ecommerce.data.source.remote.repository.register.RegisterRepository
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.repository.register.IRegisterRepository
import coding.faizal.ecommerce.domain.usecase.help.HelpUseCase
import coding.faizal.ecommerce.domain.usecase.help.impl.HelpUseCaseImpl
import coding.faizal.ecommerce.domain.usecase.register.RegisterUseCase
import coding.faizal.ecommerce.domain.usecase.register.impl.RegisterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideRegisterRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideRegisterRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterModule {



    @Binds
    @ProvideRegisterRepositoryImpl
    abstract fun provideRepositoryImpl(registerUseCaseImpl : RegisterUseCaseImpl): RegisterUseCase

    @Binds
    @ProvideRegisterRepository
    abstract fun provideRepository(registerRepository: RegisterRepository): IRegisterRepository


}