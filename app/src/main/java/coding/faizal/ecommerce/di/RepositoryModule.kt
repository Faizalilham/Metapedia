package coding.faizal.ecommerce.di


import coding.faizal.ecommerce.data.source.local.repository.address.AddressRepository
import coding.faizal.ecommerce.data.source.local.repository.help.HelpRepository
import coding.faizal.ecommerce.data.source.remote.repository.forgetpassword.ForgetPasswordRepository
import coding.faizal.ecommerce.data.source.remote.repository.login.LoginRepository
import coding.faizal.ecommerce.data.source.remote.repository.product.ProductRepository
import coding.faizal.ecommerce.data.source.remote.repository.profile.ProfileRepository
import coding.faizal.ecommerce.data.source.remote.repository.register.RegisterRepository
import coding.faizal.ecommerce.data.source.remote.repository.verification.VerificationRepository
import coding.faizal.ecommerce.domain.repository.address.IAddressRepository
import coding.faizal.ecommerce.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.repository.login.ILoginRepository
import coding.faizal.ecommerce.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.domain.repository.profile.IProfileRepository
import coding.faizal.ecommerce.domain.repository.register.IRegisterRepository
import coding.faizal.ecommerce.domain.repository.verification.IVerificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun registerRepository(registerRepository: RegisterRepository): IRegisterRepository

    @Binds
    abstract fun helpRepository(helpRepository: HelpRepository):IHelpRepository

    @Binds
    abstract fun verificationRepository(VerificationRepository: VerificationRepository):IVerificationRepository

    @Binds
    abstract fun loginRepository(loginRepository: LoginRepository):ILoginRepository

    @Binds
    abstract fun forgetPasswordRepository(forgetPasswordRepository: ForgetPasswordRepository): IForgetPasswordRepository

    @Binds
    abstract  fun profileRepository(profileRepository : ProfileRepository) : IProfileRepository

    @Binds
    abstract  fun addressRepository(AddressRepository : AddressRepository) : IAddressRepository

    @Binds
    abstract  fun productRepository(productRepository : ProductRepository) : IProductRepository
}