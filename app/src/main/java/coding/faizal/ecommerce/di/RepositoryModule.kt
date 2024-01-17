package coding.faizal.ecommerce.di



import coding.faizal.ecommerce.data.source.local.repository.help.HelpRepository
import coding.faizal.ecommerce.data.source.remote.repository.authentication.AuthenticationRepository
import coding.faizal.ecommerce.data.source.remote.repository.forgetpassword.ForgetPasswordRepository
import coding.faizal.ecommerce.data.source.remote.repository.product.ProductRepository
import coding.faizal.ecommerce.data.source.remote.repository.user.UserRepository
import coding.faizal.ecommerce.data.source.remote.repository.verification.VerificationRepository
import coding.faizal.ecommerce.domain.repository.authentication.IAuthenticationRepository
import coding.faizal.ecommerce.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.domain.repository.user.IUserRepository
import coding.faizal.ecommerce.domain.repository.verification.IVerificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun authenticationRepository(authenticationRepository: AuthenticationRepository): IAuthenticationRepository

    @Binds
    abstract fun helpRepository(helpRepository: HelpRepository):IHelpRepository

    @Binds
    abstract fun verificationRepository(VerificationRepository: VerificationRepository):IVerificationRepository

    @Binds
    abstract fun forgetPasswordRepository(forgetPasswordRepository: ForgetPasswordRepository): IForgetPasswordRepository

    @Binds
    abstract  fun userRepository(profileRepository : UserRepository) : IUserRepository

    @Binds
    abstract  fun productRepository(productRepository : ProductRepository) : IProductRepository
}