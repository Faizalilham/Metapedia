package android.faizal.movieapp.core.di


import coding.faizal.ecommerce.data.repository.local.help.HelpRepository
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.repository.login.IloginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [DatabaseModule::class,NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(iLoginRepository: IloginRepository):IloginRepository

    @Binds
    abstract fun helpRepository(helpRepository: HelpRepository):IHelpRepository
}