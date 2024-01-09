package coding.faizal.ecommerce.domain.usecase.login.impl

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.repository.login.ILoginRepository
import coding.faizal.ecommerce.domain.usecase.login.LoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val ILoginRepository : ILoginRepository) : LoginUseCase {
    override fun praLogin(email: String): Flow<Resource<Any>>  = ILoginRepository.praLogin(email)

    override fun doLogin(email: String, password: String) = ILoginRepository.doLogin(email,password)
}