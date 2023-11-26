package coding.faizal.ecommerce.di.help


import coding.faizal.ecommerce.data.repository.local.help.HelpRepository
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.usecase.help.HelpUseCase
import coding.faizal.ecommerce.domain.usecase.help.impl.HelpUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideHelpRepositoryImpl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideHelpRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class HelpModule {



    @Binds
    @ProvideHelpRepositoryImpl
    abstract fun provideRepositoryImpl(helpUseCaseImpl : HelpUseCaseImpl):HelpUseCase

    @Binds
    @ProvideHelpRepository
    abstract fun provideRepository(helpRepository: HelpRepository):IHelpRepository


}


